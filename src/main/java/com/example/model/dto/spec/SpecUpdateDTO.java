package com.example.model.dto.spec;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.util.List;

@Data
public class SpecUpdateDTO {
    @Size(max = 20, message = "规格名称最长20个字符")
    private String specName;

    @Size(min = 1, message = "至少需要1个规格值")
    private List<@NotBlank String> specValues;
} 