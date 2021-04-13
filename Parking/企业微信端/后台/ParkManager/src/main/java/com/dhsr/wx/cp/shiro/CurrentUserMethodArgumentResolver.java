package com.dhsr.wx.cp.shiro;

import com.dhsr.wx.cp.annotation.CurrentUser;
import com.dhsr.wx.cp.entity.Operator;
import com.dhsr.wx.cp.exception.UnauthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 增加方法注入，将含有 @CurrentUser 注解的方法参数注入当前登录用户
 *
 * @author liuxiaogang
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Operator.class)
            && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        System.out.println("=============222=============================");
        Operator operator = (Operator) webRequest.getAttribute("currentUser", RequestAttributes.SCOPE_REQUEST);
        if (operator == null||operator.getUserInnerId()==null) {
            throw new UnauthorizedException("获取用户信息失败");
        }
        return operator;
    }
}
