package my.application.api.services.member;

import lombok.RequiredArgsConstructor;
import my.application.api.dto.member.SignUp;
import my.domain.mysql.entities.MemberEntity;
import my.domain.mysql.repositories.member.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCRUDService {
    private final MemberRepository memberRepository;

    public boolean createMember(SignUp member) {
        memberRepository.save(MemberEntity.builder().id(member.getId()).password(member.getPassword()).name(member.getName()).build());
        return true;
    }

    public List<MemberEntity> memberEntities() {
        return memberRepository.findAll();
    }

    public MemberEntity getMember(Integer memNo) {
        return memberRepository.findByMemNo(memNo);
    }

}
