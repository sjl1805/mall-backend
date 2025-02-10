package com.example.model.dto.address;

import com.example.model.enums.UserAddressStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "地址创建请求参数")
@Data
public class AddressCreateDTO {
    @Schema(description = "收货人姓名", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 20, example = "张三")
    @NotBlank(message = "收货人姓名不能为空")
    @Size(max = 20, message = "姓名最长20个字符")
    private String receiverName;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13800138000")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String receiverPhone;

    @Schema(description = "省份", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 20, example = "广东省")
    @NotBlank(message = "省份不能为空")
    @Size(max = 20, message = "省份最长20个字符")
    private String province;

    @Schema(description = "城市", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 20, example = "深圳市")
    @NotBlank(message = "城市不能为空")
    @Size(max = 20, message = "城市最长20个字符")
    private String city;

    @Schema(description = "区县", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 20, example = "南山区")
    @NotBlank(message = "区县不能为空")
    @Size(max = 20, message = "区县最长20个字符")
    private String district;

    @Schema(description = "详细地址", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 100, example = "科技园路123号")
    @NotBlank(message = "详细地址不能为空")
    @Size(max = 100, message = "详细地址最长100个字符")
    private String detailAddress;

    @Schema(description = "是否默认地址", requiredMode = Schema.RequiredMode.REQUIRED, implementation = UserAddressStatusEnum.class)
    @NotNull(message = "默认地址状态不能为空")
    private UserAddressStatusEnum isDefault;
} 