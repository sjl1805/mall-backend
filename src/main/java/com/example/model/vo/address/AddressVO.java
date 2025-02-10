package com.example.model.vo.address;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "地址信息VO")
public class AddressVO {
    @Schema(name = "id", description = "地址ID", example = "1")
    private Long id;

    @Schema(name = "receiverName", description = "收货人姓名", example = "张三")
    private String receiverName;

    @Schema(name = "receiverPhone", description = "收货人手机", example = "13800138000")
    private String receiverPhone;

    @Schema(name = "region", description = "省市区组合", example = "广东省 深圳市 南山区")
    private String region;

    @Schema(name = "detailAddress", description = "详细地址", example = "科技园路123号")
    private String detailAddress;

    @Schema(name = "isDefault", description = "是否默认地址", example = "true")
    private Boolean isDefault;

    @Schema(name = "lastUsedTime", description = "最后使用时间", example = "2024-03-20T14:30:00")
    private LocalDateTime lastUsedTime;
} 