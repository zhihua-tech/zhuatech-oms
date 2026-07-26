/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oms.service;

import cn.zhuatech.oms.common.BusinessException;
import cn.zhuatech.oms.model.UserAccount;
import cn.zhuatech.oms.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    private final UserRepository users;

    public CurrentUserService(UserRepository users) { this.users = users; }

    public UserAccount get() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return users.findByUsername(username).orElseThrow(() -> new BusinessException("当前用户不存在"));
    }
}

