package com.example.model.dto.order;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDetailDTO {
    private String orderNo;
    private BigDecimal totalAmount;
    private BigDecimal payAmount;
    private String statusDesc;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private LocalDateTime paymentTime;
    private String logisticsCompany;
    private String trackingNumber;
    private List<OrderItemDTO> items;
} 