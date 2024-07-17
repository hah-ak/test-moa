package my.application.member.services.member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.member.dto.signUp.SignUp;
import my.application.member.entities.mysql.MemberEntity;
import my.application.member.repositories.mysql.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSecurityService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberEntity signUpProcess(SignUp signUp) {

        MemberEntity memberEntity = new MemberEntity(
                signUp.id(),
                passwordEncoder.encode(signUp.password()),
                signUp.name(),
                signUp.imageName()
        );
        MemberEntity save = memberRepository.save(memberEntity);

        return save;
    }
}
