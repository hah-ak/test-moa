package my.application.gateway.services.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.gateway.entities.mysql.member.MemberPrivilege;
import my.application.gateway.entities.mysql.member.MemberEntity;
import my.application.gateway.repositories.mysql.member.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

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
