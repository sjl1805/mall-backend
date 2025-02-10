package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.dto.favorite.FavoriteFolderDTO;
import com.example.model.dto.favorite.FavoriteFolderPageQueryDTO;
import com.example.model.entity.FavoriteFolder;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 31815
 * @description 针对表【favorite_folder(收藏夹表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:09:17
 * @Entity gg.model.FavoriteFolder
 */
@Mapper
public interface FavoriteFolderMapper extends BaseMapper<FavoriteFolder> {

    @Results(id = "folderMap", value = {
            @Result(property = "isPublic", column = "is_public"),
            @Result(property = "itemCount", column = "item_count"),
            @Result(property = "createTime", column = "create_time")
    })
    @Select("<script>" +
            "SELECT id, name, description, is_public AS isPublic, " +
            "sort, item_count AS itemCount, create_time " +
            "FROM favorite_folder " +
            "WHERE user_id = #{userId} " +
            "<if test='query.name != null'>AND name LIKE CONCAT('%',#{query.name},'%')</if>" +
            "<if test='query.status != null'>AND is_public = #{query.status}</if>" +
            "ORDER BY sort ASC, create_time DESC" +
            "</script>")
    IPage<FavoriteFolderDTO> selectUserFolders(Page<FavoriteFolderDTO> page,
                                              @Param("userId") Long userId,
                                              @Param("query") FavoriteFolderPageQueryDTO query);

    @Update("UPDATE favorite_folder SET item_count = item_count + #{increment} " +
            "WHERE id = #{folderId} AND user_id = #{userId}")
    int updateItemCount(@Param("folderId") Long folderId,
                        @Param("increment") int increment,
                        @Param("userId") Long userId);

    @Update("<script>" +
            "UPDATE favorite_folder SET sort = CASE id " +
            "<foreach collection='folders' item='folder'>" +
            "WHEN #{folder.id} THEN #{folder.sort} " +
            "</foreach>" +
            "END WHERE id IN " +
            "<foreach collection='folders' item='folder' open='(' separator=',' close=')'>" +
            "#{folder.id}" +
            "</foreach>" +
            "</script>")
    int batchUpdateSort(@Param("folders") List<FavoriteFolderDTO> folders);

    @Select("SELECT COUNT(*) FROM favorite_folder " +
            "WHERE id = #{folderId} AND user_id = #{userId}")
    @Options(useCache = true)
    int checkFolderOwnership(@Param("userId") Long userId,
                             @Param("folderId") Long folderId);
}





