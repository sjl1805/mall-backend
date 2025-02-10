package com.example.model.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "收货人信息VO")

public class ReceiverInfoVO {
    @Schema(name = "name", description = "收货人姓名", example = "张三")
    private String name;


    @Schema(name = "phone", description = "联系电话", example = "13800138000")
    private String phone;

    @Schema(name = "address", description = "详细地址", example = "北京市朝阳区xx街道xx号")
    private String address;
}