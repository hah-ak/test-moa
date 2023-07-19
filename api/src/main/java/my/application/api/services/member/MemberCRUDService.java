package my.application.api.services.member;

import lombok.RequiredArgsConstructor;
import my.application.api.entities.mysql.MemberEntity;
import my.application.api.repositories.mysql.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
