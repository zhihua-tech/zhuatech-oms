/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oms.dto;

import cn.zhuatech.oms.model.UserAccount;
import jakarta.validation.constraints.NotBlank;

public final class AuthDto {
    private AuthDto() {}

    public record LoginRequest(
        @NotBlank(message = "请输入用户名") String username,
        @NotBlank(message = "请输入密码") String password) {}

    public record UserView(Long id, String username, String fullName, String role, String department) {
        public static UserView from(UserAccount user) {
            return new UserView(user.getId(), user.getUsername(), user.getFullName(),
                user.getRole().name(), user.getDepartment());
        }
    }

    public record LoginResponse(String token, UserView user) {}
}

