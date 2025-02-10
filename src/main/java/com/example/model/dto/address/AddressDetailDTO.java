package com.example.model.dto.address;

import lombok.Data;

@Data
public class AddressDetailDTO {
    private Long addressId;
    private String receiverName;
    private String receiverPhone;
    private String fullAddress;
    private String isDefaultDesc;
} 