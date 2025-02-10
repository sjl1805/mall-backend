package com.example.model.dto.spec;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.util.List;

@Data
public class SpecCreateDTO {
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @NotBlank(message = "规格名称不能为空")
    @Size(max = 20, message = "规格名称最长20个字符")
    private String specName;

    @NotNull(message = "规格值不能为空")
    @Size(min = 1, message = "至少需要1个规格值")
    private List<@NotBlank String> specValues;
} 