package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.dto.favorite.FavoriteFolderDTO;
import com.example.model.dto.favorite.FavoriteFolderPageQueryDTO;
import com.example.model.entity.FavoriteFolder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author 31815
 * @description 针对表【favorite_folder(收藏夹表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:09:17
 * @Entity gg.model.FavoriteFolder
 */
@Mapper
public interface FavoriteFolderMapper extends BaseMapper<FavoriteFolder> {

    /**
     * 分页查询用户收藏夹
     */
    @Select("<script>" +
            "SELECT id, name, description, is_public AS isPublic, " +
            "sort, item_count AS itemCount, create_time " +
            "FROM favorite_folder " +
            "WHERE user_id = #{userId} " +
            "<if test='query.name != null'>AND name LIKE CONCAT('%',#{query.name},'%')</if>" +
            "<if test='query.status != null'>AND is_public = #{query.status}</if>" +
            "ORDER BY sort ASC, create_time DESC" +
            "</script>")
    List<FavoriteFolderDTO> selectUserFolders(Page<FavoriteFolderDTO> page,
                                           @Param("userId") Long userId,
                                           @Param("query") FavoriteFolderPageQueryDTO query);

    /**
     * 更新收藏夹项目数量
     */
    @Update("UPDATE favorite_folder SET item_count = item_count + #{increment} " +
            "WHERE id = #{folderId}")
    int updateItemCount(@Param("folderId") Long folderId,
                      @Param("increment") int increment);

    /**
     * 批量更新收藏夹排序
     */
    @Update("<script>" +
            "<foreach collection='folders' item='folder' separator=';'>" +
            "UPDATE favorite_folder SET sort = #{folder.sort} WHERE id = #{folder.id}" +
            "</foreach>" +
            "</script>")
    int batchUpdateSort(@Param("folders") List<FavoriteFolderDTO> folders);

    /**
     * 检查收藏夹是否属于用户
     */
    @Select("SELECT COUNT(*) FROM favorite_folder " +
            "WHERE id = #{folderId} AND user_id = #{userId}")
    int checkFolderOwnership(@Param("userId") Long userId,
                           @Param("folderId") Long folderId);
}





