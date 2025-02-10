package com.example.model.dto.address;

import lombok.Data;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "管理员视角地址详情")
@Data
public class AdminAddressDTO {
    @Schema(description = "地址ID", example = "1001")
    private Long id;
    
    @Schema(description = "用户ID", example = "12345")
    private Long userId;
    
    @Schema(description = "用户名", example = "john_doe")
    private String userName;
    
    @Schema(description = "收货人姓名", example = "张三")
    private String receiverName;
    
    @Schema(description = "收货人电话", example = "13800138000")
    private String receiverPhone;
    
    @Schema(description = "完整地址", example = "广东省深圳市南山区科技园路123号")
    private String fullAddress;
    
    @Schema(description = "默认地址状态描述", example = "是")
    private String isDefaultDesc;
    
    @Schema(description = "创建时间", example = "2024-03-15 10:00:00")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间", example = "2024-03-15 10:05:00")
    private LocalDateTime updateTime;
} 