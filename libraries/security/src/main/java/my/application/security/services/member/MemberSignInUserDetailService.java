package my.application.security.services.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.domain.mysql.entities.MemberEntity;
import my.domain.mysql.repositories.member.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSignInUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<MemberEntity> memberEntity1 = getMemberEntity(id);
        MemberEntity memberEntity = memberEntity1.orElseThrow(() -> new UsernameNotFoundException("user Not found"));
        return new MemberSignInUserDetails(memberEntity);
    }

    private Optional<MemberEntity> getMemberEntity(String id) {
        return Optional.ofNullable(memberRepository.findById(id));
    }
}
