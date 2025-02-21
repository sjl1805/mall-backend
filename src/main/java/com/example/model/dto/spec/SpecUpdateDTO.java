package com.example.model.dto.spec;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Schema(description = "规格更新请求参数")
@Data
public class SpecUpdateDTO {
    @Schema(description = "规格名称", maxLength = 20, example = "颜色")
    @Size(max = 20, message = "规格名称最长20个字符")
    private String specName;

    @Schema(description = "规格值列表", example = "[\"白色\",\"黑色\",\"蓝色\"]")
    @Size(min = 1, message = "至少需要1个规格值")
    private List<@NotBlank String> specValues;
} 