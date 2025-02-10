package com.example.model.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


/**
 * 地址信息VO
 */
public class UserAddressVO {
    @Schema(name = "地址ID", example = "1")
    private Long id;



    @Schema(name = "收货人姓名", required = true, example = "张三")
    @NotBlank(message = "收货人姓名不能为空")
    @Size(max = 20, message = "姓名最长20个字符")
    private String receiverName;



    @Schema(name = "收货人手机号", required = true, example = "13800138000")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String receiverPhone;



    @Schema(name = "省份", required = true, example = "广东省")
    @NotBlank(message = "省份不能为空")
    private String province;



    @Schema(name = "城市", required = true, example = "深圳市")
    @NotBlank(message = "城市不能为空")
    private String city;



    @Schema(name = "区县", required = true, example = "南山区")
    @NotBlank(message = "区县不能为空")
    private String district;



    @Schema(name = "详细地址", required = true, example = "科技南路123号")
    @NotBlank(message = "详细地址不能为空")
    @Size(max = 100, message = "地址最长100个字符")
    private String detailAddress;



    @Schema(name = "是否默认地址", example = "true")
    private Boolean isDefault;



    // Getters and Setters
} 