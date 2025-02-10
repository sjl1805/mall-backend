package com.example.model.dto.address;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "地址分页查询参数")
@Data
public class AddressPageQueryDTO {
    @Schema(description = "收货人姓名（模糊查询）", example = "张")
    private String receiverName;

    @Schema(description = "手机号（模糊查询）", example = "1380")
    private String receiverPhone;

    @Schema(description = "省份（精确查询）", example = "广东省")
    private String province;

    @Schema(description = "城市（精确查询）", example = "深圳市")
    private String city;
} 