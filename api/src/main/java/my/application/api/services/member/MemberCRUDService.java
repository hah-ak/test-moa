package my.application.api.services.member;

import lombok.RequiredArgsConstructor;
import my.application.api.entities.mysql.MemberEntity;
import my.application.api.repositories.mysql.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberCRUDService {
    private final MemberRepository memberRepository;

    public MemberEntity createMember(MemberEntity member) {
        return memberRepository.save(member);
    }

    public List<MemberEntity> memberEntities() {
        return memberRepository.findAll();
    }
    public MemberEntity getMember(Integer memNo) {return memberRepository.findByMemNo(memNo);}

    public MemberEntity login(String id, String password) {
        MemberEntity memberEntity = getMemberEntity(id).orElseThrow(NoSuchElementException::new);

        if (!memberEntity.getPassword().equals(password)) {
            throw new NoSuchElementException();
        }

        return memberEntity;
    }

    private Optional<MemberEntity> getMemberEntity(String id) {
        return Optional.ofNullable(memberRepository.findById(id));
    }
}
