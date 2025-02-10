package com.example.model.dto.address;

import com.example.model.enums.UserAddressStatusEnum;
import lombok.Data;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "地址更新请求参数")
@Data
public class AddressUpdateDTO {
    @Schema(description = "收货人姓名", maxLength = 20, example = "李四")
    @Size(max = 20, message = "姓名最长20个字符")
    private String receiverName;

    @Schema(description = "手机号", example = "13900139000")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String receiverPhone;

    @Schema(description = "省份", maxLength = 20, example = "广东省")
    @Size(max = 20, message = "省份最长20个字符")
    private String province;

    @Schema(description = "城市", maxLength = 20, example = "广州市")
    @Size(max = 20, message = "城市最长20个字符")
    private String city;

    @Schema(description = "区县", maxLength = 20, example = "天河区")
    @Size(max = 20, message = "区县最长20个字符")
    private String district;

    @Schema(description = "详细地址", maxLength = 100, example = "珠江新城88号")
    @Size(max = 100, message = "详细地址最长100个字符")
    private String detailAddress;

    @Schema(description = "是否默认地址", implementation = UserAddressStatusEnum.class)
    private UserAddressStatusEnum isDefault;
} 