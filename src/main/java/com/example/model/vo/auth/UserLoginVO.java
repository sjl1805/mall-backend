package com.example.model.vo.auth;

import com.example.model.vo.user.UserBasicVO;
import lombok.Data;

/**
 * {@code @user} 31815
 * {@code @date}  2025/2/10
 */
@Data
public class UserLoginVO {
    private String token;
    private UserBasicVO userInfo;
}
