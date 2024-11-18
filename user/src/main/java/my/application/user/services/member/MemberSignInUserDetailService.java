package my.application.user.services.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.user.entities.mysql.MemberEntity;
import my.application.user.repositories.mysql.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return new MemberSignInUserDetails(memberEntity.getId(), memberEntity.getName(), memberEntity.getPassword(), new ArrayList<>());
    }

    private Optional<MemberEntity> getMemberEntity(String id) {
        return Optional.ofNullable(memberRepository.findById(id));
    }
}
