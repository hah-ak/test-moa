package my.application.gateway.services.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.gateway.dto.signIn.SignIn;
import my.application.gateway.dto.signUp.SignUp;
import my.application.gateway.entities.mysql.member.MemberEntity;
import my.application.gateway.filter.authority.MemberUserRoleAuthority;
import my.application.gateway.repositories.mysql.member.MemberRepository;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSecurityService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberEntity signUpProcess(SignUp signUp) {
        MemberEntity memberEntity = MemberEntity.builder()
                .id(signUp.id())
                .password(passwordEncoder.encode(signUp.password()))
                .name(signUp.name())
                .imageName(signUp.imageName())
                .role(new MemberUserRoleAuthority().getAuthority())
                .build();
        MemberEntity save = memberRepository.save(memberEntity);

        return save;
    }

    public MemberEntity signInProcess(SignIn signIn) {

        MemberEntity byId = memberRepository.findById(signIn.id());
        if (byId.getPassword().equals(passwordEncoder.encode(signIn.password()))) {
            HmacAlgorithms hmacSha256 = HmacAlgorithms.HMAC_SHA_256;
//            HmacUtils.getInitializedMac()
        }
        return byId;
    }
}
