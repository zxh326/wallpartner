package org.neusovo.wallpartner.wx.annotation.support;

import org.neusovo.wallpartner.db.domain.WxUser;
import org.neusovo.wallpartner.wx.annotation.LoginUser;
import org.neusovo.wallpartner.wx.util.JWTUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    public static final String LOGIN_TOKEN_KEY = "Authorization";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Long.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {

//        return new Integer(1);
        String token = request.getHeader(LOGIN_TOKEN_KEY);
        if (token == null || token.isEmpty()) {
            return null;
        }
        return JWTUtil.unsign(token, Long.class);
    }
}
