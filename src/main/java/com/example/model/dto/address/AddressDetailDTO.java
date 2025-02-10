package com.example.model.dto.address;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "地址详情响应数据")
@Data
public class AddressDetailDTO {
    @Schema(description = "地址ID", example = "1001")
    private Long addressId;
    
    @Schema(description = "收货人姓名", example = "张三")
    private String receiverName;
    
    @Schema(description = "收货人电话", example = "13800138000")
    private String receiverPhone;
    
    @Schema(description = "完整地址", example = "广东省深圳市南山区科技园路123号")
    private String fullAddress;
    
    @Schema(description = "默认地址状态描述", example = "是")
    private String isDefaultDesc;
} 