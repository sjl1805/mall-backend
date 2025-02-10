package com.example.model.dto.spec;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "规格创建请求参数")
@Data
public class SpecCreateDTO {
    @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3001")
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @Schema(description = "规格名称", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 20, example = "颜色")
    @NotBlank(message = "规格名称不能为空")
    @Size(max = 20, message = "规格名称最长20个字符")
    private String specName;

    @Schema(description = "规格值列表", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"白色\",\"黑色\"]")
    @NotNull(message = "规格值不能为空")
    @Size(min = 1, message = "至少需要1个规格值")
    private List<@NotBlank String> specValues;
} 