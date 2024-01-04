package my.application.gateway.repositories.mysql.member;

import my.application.gateway.entities.mysql.member.MemberAuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAuthorityRepository extends JpaRepository<MemberAuthorityEntity, Integer> {
}
