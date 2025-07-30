package com.nexters.teamace.auth.presentation;

import com.nexters.teamace.common.presentation.AuthUser;
import com.nexters.teamace.common.presentation.UserInfo;
import com.nexters.teamace.user.application.GetUserResult;
import com.nexters.teamace.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthUser.class);
    }

    @Override
    public Object resolveArgument(
            @NotNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            @NotNull NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory)
            throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof UserDetails userDetails)) {
            throw new IllegalArgumentException(
                    "Invalid principal type: " + principal.getClass().getName());
        }

        GetUserResult userResult = userService.getUserByUsername(userDetails.getUsername());
        return new UserInfo(userResult.id(), userResult.username(), userResult.nickname());
    }
}
