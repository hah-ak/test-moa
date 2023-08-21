package my.application.security.services.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.security.entities.signUp.SignUp;
import my.domain.mysql.entities.MemberEntity;
import my.domain.mysql.repositories.member.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSecurityService {

    private final MemberRepository memberRepository;
    public MemberEntity signUpProcess(SignUp signUp) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        MemberEntity memberEntity = MemberEntity.builder()
                .id(signUp.id())
                .password(bCryptPasswordEncoder.encode(signUp.password()))
                .name(signUp.name())
                .imageName(signUp.imageName())
                .build();
        MemberEntity save = memberRepository.save(memberEntity);
        log.info("save memNo : {}",save.getMemNo());
        return save;
    }
}
