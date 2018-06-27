package com.portal.common.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by Administrator on 2017/6/9.
 */
public final class UserUtil {

    /**
     * 获取当前登录者对象
     *
     * @param <T> the type parameter
     * @return the current user
     */
    @SuppressWarnings("unchecked")
    public static <T extends UserDetails> T getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (T) authentication.getPrincipal();
    }
}
