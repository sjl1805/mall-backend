package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.entity.FavoriteFolder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 31815
 * @description 针对表【favorite_folder(收藏夹表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:09:17
 * @Entity gg.model.FavoriteFolder
 */
@Mapper
public interface FavoriteFolderMapper extends BaseMapper<FavoriteFolder> {

}





