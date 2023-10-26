package my.application.security.services.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

//implements SecurityContextRepository
@Slf4j
public class MemberSignInSecurityContextRepository {

//    @Override
//    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
//        return null;
//    }

//    @Override
//    public DeferredSecurityContext loadDeferredContext(HttpServletRequest request) {
//        return SecurityContextRepository.super.loadDeferredContext(request);
//    }
//
//    @Override
//    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
//
//    }
//
//    @Override
//    public boolean containsContext(HttpServletRequest request) {
//        log.error("security context");request.getUserPrincipal();
//        return false;
//    }
}
