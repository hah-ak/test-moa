package my.application.security.filter.manager;

//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class MemberAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
//
//    private final MemberUserRoleAuthority memberUserRoleAuthority;
//    @Override
//    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
//        log.error("authorization manager check");
//        return new AuthorizationDecision(authentication.get().getAuthorities().contains(memberUserRoleAuthority));
//    }
//
//    @Override
//    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
//        log.error("authorization verify");
//        AuthorizationManager.super.verify(authentication, object);
//    }
//}
