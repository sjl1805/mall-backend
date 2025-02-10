package com.example.model.dto.address;

import com.example.model.enums.UserAddressStatusEnum;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class AddressCreateDTO {
    @NotBlank(message = "收货人姓名不能为空")
    @Size(max = 20, message = "姓名最长20个字符")
    private String receiverName;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String receiverPhone;

    @NotBlank(message = "省份不能为空")
    @Size(max = 20, message = "省份最长20个字符")
    private String province;

    @NotBlank(message = "城市不能为空")
    @Size(max = 20, message = "城市最长20个字符")
    private String city;

    @NotBlank(message = "区县不能为空")
    @Size(max = 20, message = "区县最长20个字符")
    private String district;

    @NotBlank(message = "详细地址不能为空")
    @Size(max = 100, message = "详细地址最长100个字符")
    private String detailAddress;

    @NotNull(message = "默认地址状态不能为空")
    private UserAddressStatusEnum isDefault;
} 