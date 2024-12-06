package my.application.user.services.member;

import lombok.RequiredArgsConstructor;
import my.application.user.entities.mysql.MemberEntity;
import my.application.user.repositories.mysql.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public List<MemberEntity> memberEntities() {
        return memberRepository.findAll();
    }

    public MemberEntity getMember(Long memNo) {
        return memberRepository.findByMemNo(memNo);
    }
    public MemberEntity getMember(String id) {
        return memberRepository.findById(id);
    }


}
