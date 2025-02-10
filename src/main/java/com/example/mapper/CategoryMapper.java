package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.model.dto.category.AdminCategoryDTO;
import com.example.model.dto.category.CategoryPageQueryDTO;
import com.example.model.dto.category.CategoryTreeDTO;
import com.example.model.entity.Category;
import com.example.model.enums.CategoryStatusEnum;
import org.apache.ibatis.annotations.*;


import java.util.List;

/**
 * @author 31815
 * @description 针对表【category(商品分类表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:09:24
 * @Entity gg.model.Category
 */
public interface CategoryMapper extends BaseMapper<Category> {

    @Results(id = "categoryTreeMap", value = {
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "statusDesc", column = "status"),
            @Result(property = "createTime", column = "create_time")
    })
    @Select("WITH RECURSIVE cte AS (" +
            "SELECT * FROM category WHERE parent_id = 0" +
            " UNION ALL " +
            "SELECT c.* FROM category c INNER JOIN cte ON c.parent_id = cte.id" +
            ") SELECT * FROM cte ORDER BY sort ASC")
    List<CategoryTreeDTO> selectCategoryTree();

    @Results(id = "adminCategoryMap", value = {
            @Result(property = "parentName", column = "parent_name"),
            @Result(property = "statusDesc", column = "status"),
            @Result(property = "createTime", column = "create_time")
    })
    @Select("<script>" +
            "SELECT c.*, p.name as parent_name FROM category c " +
            "LEFT JOIN category p ON c.parent_id = p.id " +
            "<where>" +
            "   <if test='query.name != null'>AND c.name LIKE CONCAT('%',#{query.name},'%')</if>" +
            "   <if test='query.status != null'>AND c.status = #{query.status}</if>" +
            "   <if test='query.level != null'>AND c.level = #{query.level}</if>" +
            "</where>" +
            "ORDER BY c.sort ASC, c.create_time DESC" +
            "</script>")
    IPage<CategoryPageQueryDTO> selectAdminCategoryList(@Param("query") CategoryPageQueryDTO query);



    @Update("UPDATE category SET status = #{status} " +
            "WHERE id = #{id} " +
            "AND EXISTS (SELECT 1 FROM products WHERE category_id = #{id} AND merchant_id = #{merchantId})")
    int updateStatus(@Param("id") Long id,
                     @Param("status") CategoryStatusEnum status,
                     @Param("merchantId") Long merchantId);

    @Options(useCache = true)
    @Select("SELECT * FROM category WHERE parent_id = #{parentId} ORDER BY sort ASC")
    List<CategoryTreeDTO> selectByParentId(@Param("parentId") Long parentId);

    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE)
    @Select("SELECT id, parent_id, name, icon, level, sort, status FROM category ORDER BY parent_id, sort")
    List<CategoryTreeDTO> selectAllCategories();

    @Select("SELECT c.*, p.name as parent_name FROM category c " +
            "LEFT JOIN category p ON c.parent_id = p.id " +
            "WHERE c.parent_id = #{parentId} " +
            "ORDER BY c.sort ASC")
    List<AdminCategoryDTO> selectByParentIdWithPage(CategoryPageQueryDTO page, @Param("parentId") Long parentId);
}




