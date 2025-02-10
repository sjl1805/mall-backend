package com.example.model.dto.spec;

import lombok.Data;
import java.util.List;

@Data
public class SpecDetailDTO {
    private Long id;
    private String productName;
    private String specName;
    private List<String> specValues;
} 