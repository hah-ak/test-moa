package my.application.security.services.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.security.model.login.UserLogin;
import my.domain.mysql.entities.MemberEntity;
import my.domain.mysql.repositories.member.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

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
}
