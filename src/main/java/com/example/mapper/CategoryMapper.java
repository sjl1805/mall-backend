package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.dto.category.AdminCategoryDTO;
import com.example.model.dto.category.CategoryTreeDTO;
import com.example.model.entity.Category;
import com.example.model.enums.CategoryStatusEnum;
import com.example.model.dto.category.CategoryPageQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import java.util.List;

/**
 * @author 31815
 * @description 针对表【category(商品分类表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:09:24
 * @Entity gg.model.Category
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 获取分类树形结构
     */
    @Select("WITH RECURSIVE cte AS (" +
            "SELECT * FROM category WHERE parent_id = 0" +
            " UNION ALL " +
            "SELECT c.* FROM category c INNER JOIN cte ON c.parent_id = cte.id" +
            ") SELECT * FROM cte ORDER BY sort ASC")
    List<CategoryTreeDTO> selectCategoryTree();

    /**
     * 分页查询分类列表（管理员用）
     */
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
    List<AdminCategoryDTO> selectAdminCategoryList(Page<AdminCategoryDTO> page, @Param("query") CategoryPageQueryDTO query);

    /**
     * 更新分类状态
     */
    @Update("UPDATE category SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") CategoryStatusEnum status);

    /**
     * 根据父分类ID查询子分类
     */
    @Select("SELECT * FROM category WHERE parent_id = #{parentId} ORDER BY sort ASC")
    List<CategoryTreeDTO> selectByParentId(@Param("parentId") Long parentId);

    /**
     * 查询所有分类（用于构建树形结构）
     */
    @Select("SELECT id, parent_id, name, icon, level, sort, status FROM category ORDER BY parent_id, sort")
    List<CategoryTreeDTO> selectAllCategories();

    /**
     * 根据父ID分页查询子分类
     */
    @Select("SELECT c.*, p.name as parent_name FROM category c " +
            "LEFT JOIN category p ON c.parent_id = p.id " +
            "WHERE c.parent_id = #{parentId} " +
            "ORDER BY c.sort ASC")
    List<AdminCategoryDTO> selectByParentIdWithPage(Page<AdminCategoryDTO> page, @Param("parentId") Long parentId);
}




