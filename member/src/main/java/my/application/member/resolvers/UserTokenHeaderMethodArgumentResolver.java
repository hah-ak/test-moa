package my.application.member.resolvers;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserTokenHeaderMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//        String header = webRequest.getHeader("MY-APP-CREDENTIAL");
        String header = "107202654188028577798";
        if (header == null) {
            return null;
        } else {
            return MyAppHeaderToken.builder().id(header).build();
        }
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(MyAppHeaderToken.class);
    }
}
