package my.application.member.services.member;

import lombok.RequiredArgsConstructor;
import my.application.member.entities.mysql.member.MemberEntity;
import my.application.member.repositories.mysql.member.MemberRepository;
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
