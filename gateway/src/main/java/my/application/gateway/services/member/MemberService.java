package my.application.gateway.services.member;

import lombok.RequiredArgsConstructor;
import my.application.gateway.entities.mysql.member.MemberEntity;
import my.application.gateway.repositories.mysql.member.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public List<MemberEntity> memberEntities() {
        return memberRepository.findAll();
    }

    public MemberEntity getMember(Integer memNo) {
        return memberRepository.findByMemNo(memNo);
    }
    public MemberEntity getMember(String id) {
        return memberRepository.findById(id);
    }


}
