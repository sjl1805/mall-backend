package com.example.model.dto.category;

import com.example.model.enums.CategoryStatusEnum;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "分类创建请求参数")
@Data
public class CategoryCreateDTO {
    @Schema(description = "父分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "父分类ID不能为空")
    private Long parentId;

    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 64, example = "数码产品")
    @NotBlank(message = "分类名称不能为空")
    @Size(max = 64, message = "分类名称最长64个字符")
    private String name;

    @Schema(description = "图标URL", maxLength = 255, example = "https://example.com/icon.png")
    @Size(max = 255, message = "图标URL过长")
    private String icon;

    @Schema(description = "分类层级", requiredMode = Schema.RequiredMode.REQUIRED, example = "1", allowableValues = {"1", "2", "3"})
    @NotNull(message = "层级不能为空")
    @Min(value = 1, message = "层级最小为1")
    @Max(value = 3, message = "层级最大为3")
    private Integer level;

    @Schema(description = "排序值", minimum = "0", example = "10")
    @Min(value = 0, message = "排序值不能小于0")
    private Integer sort = 0;

    @Schema(description = "分类状态", requiredMode = Schema.RequiredMode.REQUIRED, implementation = CategoryStatusEnum.class)
    @NotNull(message = "状态不能为空")
    private CategoryStatusEnum status;
} 