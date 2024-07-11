package my.application.member.services.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.gateway.entities.mysql.member.MemberRole;
import my.application.gateway.entities.mysql.member.MemberRoleEntity;
import my.application.gateway.entities.mysql.member.RoleEntity;
import my.application.member.dto.signIn.SignIn;
import my.application.member.dto.signUp.SignUp;
import my.application.member.entities.mysql.member.MemberEntity;
import my.application.member.entities.mysql.member.MemberRole;
import my.application.member.entities.mysql.member.MemberRoleEntity;
import my.application.member.entities.mysql.member.RoleEntity;
import my.application.member.repositories.mysql.member.MemberRepository;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSecurityService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional
    public MemberEntity signUpProcess(SignUp signUp) {

        MemberEntity memberEntity = new MemberEntity(
                signUp.id(),
                passwordEncoder.encode(signUp.password()),
                signUp.name(),
                signUp.imageName()
        );
        MemberEntity save = memberRepository.save(memberEntity);

        setRole(save);

        return save;
    }

    @Transactional
    protected void setRole(MemberEntity save) {
        RoleEntity roleEntity = entityManager.find(RoleEntity.class, MemberRole.ROLE_USER);
        MemberRoleEntity memberRoleEntity = new MemberRoleEntity(save, roleEntity);
        entityManager.persist(memberRoleEntity);

    }

    public MemberEntity signInProcess(SignIn signIn) {

        MemberEntity byId = memberRepository.findById(signIn.id());
        if (byId.getPassword().equals(passwordEncoder.encode(signIn.password()))) {
            HmacAlgorithms hmacSha256 = HmacAlgorithms.HMAC_SHA_256;
        }
        return byId;
    }
}
