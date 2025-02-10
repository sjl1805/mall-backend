package com.example.model.dto.address;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdminAddressDTO {
    private Long id;
    private Long userId;
    private String userName;
    private String receiverName;
    private String receiverPhone;
    private String fullAddress;
    private String isDefaultDesc;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 