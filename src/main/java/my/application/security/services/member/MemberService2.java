package my.application.security.services.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.security.model.signIn.UserLogin;
import my.application.security.model.signUp.SignUp;
import my.domain.mysql.entities.MemberEntity;
import my.domain.mysql.repositories.member.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService2 {

    private final MemberRepository memberRepository;
    public MemberEntity login(UserLogin login) {
        MemberEntity memberEntity = getMemberEntity(login.getId()).orElseThrow(NoSuchElementException::new);

        if (!memberEntity.getPassword().equals(login.getPassword())) {
            throw new NoSuchElementException();
        }

        return memberEntity;
    }

    private Optional<MemberEntity> getMemberEntity(String id) {
        return Optional.ofNullable(memberRepository.findById(id));
    }

    public MemberEntity signUpProcess(SignUp signUp) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        MemberEntity memberEntity = MemberEntity.builder()
                .id(signUp.getId())
                .password(bCryptPasswordEncoder.encode(signUp.getPassword()))
                .name(signUp.getName())
                .imageName(signUp.getImageName())
                .build();
        MemberEntity save = memberRepository.save(memberEntity);
        log.info("save memNo : {}",save.getMemNo());
        return save;
    }
}
