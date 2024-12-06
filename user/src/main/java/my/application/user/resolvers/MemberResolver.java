package my.application.user.resolvers;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import my.application.user.dto.token.MemberLoginToken;
import my.application.user.filter.exception.AuthenticationExternalDataErrorException;
import my.application.user.util.JWTUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.text.ParseException;
import java.util.Optional;

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
        String jwt = Optional.ofNullable(servletRequest.getHeader("MY-APP-CREDENTIAL")).orElseThrow(AuthenticationExternalDataErrorException::new);
        return resolveArgument(jwt);
    }

    @NotNull
    private static MemberLoginToken resolveArgument(String request) {
        try {
            SignedJWT signedJWT = JWTUtil.parseJWT(request);
            String name = signedJWT.getJWTClaimsSet().getClaim("name").toString();
            String email = signedJWT.getJWTClaimsSet().getAudience().get(0);
            String signature = signedJWT.getSignature().decodeToString();
            String session = signedJWT.getJWTClaimsSet().getJWTID();
            return new MemberLoginToken(email, session, signature, name);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
