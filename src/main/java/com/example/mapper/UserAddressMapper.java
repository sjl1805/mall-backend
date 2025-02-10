package com.example.mapper;

import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.dto.address.AddressPageQueryDTO;
import com.example.model.entity.UserAddress;
import com.example.model.enums.UserAddressStatusEnum;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * @author 31815
 * @description 针对表【user_address(用户收货地址表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:08:40
 * @Entity gg.model.UserAddress
 */
@Mapper
public interface UserAddressMapper extends BaseMapper<UserAddress> {

    /**
     * 分页查询地址列表（管理员用）
     */
    @Results(id = "adminAddressMap", value = {
            @Result(property = "receiverName", column = "receiver_name"),
            @Result(property = "receiverPhone", column = "receiver_phone"),
            @Result(property = "isDefault", column = "is_default", typeHandler = MybatisEnumTypeHandler.class)
    })
    @Select("<script>" +
            "SELECT a.*, u.username " +
            "FROM user_address a " +
            "LEFT JOIN users u ON a.user_id = u.id " +
            "<where>" +
            "   <if test='query.receiverName != null'>AND a.receiver_name LIKE CONCAT('%',#{query.receiverName},'%')</if>" +
            "   <if test='query.receiverPhone != null'>AND a.receiver_phone LIKE CONCAT('%',#{query.receiverPhone},'%')</if>" +
            "   <if test='query.province != null'>AND a.province = #{query.province}</if>" +
            "   <if test='query.city != null'>AND a.city = #{query.city}</if>" +
            "</where>" +
            "ORDER BY a.create_time DESC" +
            "</script>")
    AddressPageQueryDTO selectAdminAddressList(@Param("query") AddressPageQueryDTO query);


    /**
     * 设置用户默认地址（带事务）
     */
    @Update("UPDATE user_address SET is_default = #{status} " +
            "WHERE user_id = #{userId} AND id = #{addressId}")
    int updateDefaultStatus(@Param("userId") Long userId,
                            @Param("addressId") Long addressId,
                            @Param("status") UserAddressStatusEnum status);

    /**
     * 获取用户默认地址
     */
    @Select("SELECT * FROM user_address " +
            "WHERE user_id = #{userId} AND is_default = 'DEFAULT' " +
            "LIMIT 1")
    Optional<UserAddress> selectDefaultAddress(@Param("userId") Long userId);


    /**
     * 批量更新地址状态
     */
    @Update("<script>" +
            "UPDATE user_address SET is_default = #{status} " +
            "WHERE id IN " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "AND user_id = #{userId} " +
            "</script>")
    int batchUpdateStatus(@Param("ids") List<Long> ids,
                          @Param("status") UserAddressStatusEnum status,
                          @Param("userId") Long userId);

    /**
     * 获取用户地址列表（按更新时间倒序）
     */
    @Select("SELECT * FROM user_address " +
            "WHERE user_id = #{userId} " +
            "ORDER BY update_time DESC")
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE)
    List<UserAddress> selectUserAddressList(@Param("userId") Long userId);
}





