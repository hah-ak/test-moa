package my.application.security.resolvers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import my.application.security.dto.token.MemberLoginToken;
import my.domain.mysql.entities.MemberEntity;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import web.core.util.MyAppCommonUtil;

public class MemberResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean assignableFrom = MemberLoginToken.class.isAssignableFrom(parameter.getParameterType());
        return assignableFrom;
    }

    @Override
    public MemberLoginToken resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return resolveArgument((HttpServletRequest) webRequest);
    }

    @NotNull
    public static MemberLoginToken resolveArgument(HttpServletRequest servletRequest) {
        return resolveArgument(servletRequest.getHeader("MY-APP-CREDENTIAL"));
    }

    @NotNull
    public static MemberLoginToken resolveArgument(String request) {
        return MyAppCommonUtil.defaultObjectMapper.convertValue(request, MemberLoginToken.class);
    }
}
