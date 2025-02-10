package com.example.model.vo.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "分类详情VO")
public class CategoryDetailVO {
    @Schema(name = "parentId", description = "父分类ID", example = "0")
    private Long parentId;

    @Schema(name = "name", description = "分类名称", required = true, example = "数码产品")
    @NotBlank(message = "分类名称不能为空")
    @Size(max = 20, message = "分类名称最长20个字符")
    private String name;

    @Schema(name = "sort", description = "排序值", example = "10")
    @Min(value = 0, message = "排序值最小为0")
    private Integer sort;

    @Schema(name = "status", description = "状态", example = "1", allowableValues = {"0", "1"})
    private Integer status;
} 