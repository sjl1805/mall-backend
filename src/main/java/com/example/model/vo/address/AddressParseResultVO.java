package com.example.model.vo.address;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "地址解析结果VO")
public class AddressParseResultVO {
    @Schema(name = "province", description = "省份", example = "广东省")
    private String province;

    @Schema(name = "city", description = "城市", example = "深圳市")
    private String city;

    @Schema(name = "district", description = "区县", example = "南山区")
    private String district;

    @Schema(name = "detailAddress", description = "详细地址", example = "科技园路123号")
    private String detailAddress;

    @Schema(name = "confidence", description = "置信度", example = "0.95")
    private Double confidence;
} 