package com.example.model.dto.address;

import com.example.model.enums.UserAddressStatusEnum;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class AddressUpdateDTO {
    @Size(max = 20, message = "姓名最长20个字符")
    private String receiverName;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String receiverPhone;

    @Size(max = 20, message = "省份最长20个字符")
    private String province;

    @Size(max = 20, message = "城市最长20个字符")
    private String city;

    @Size(max = 20, message = "区县最长20个字符")
    private String district;

    @Size(max = 100, message = "详细地址最长100个字符")
    private String detailAddress;

    private UserAddressStatusEnum isDefault;
} 