package my.domain.mysql.repositories.member;

import my.domain.mysql.entities.MemberAuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MemberAuthorityRepository extends JpaRepository<MemberAuthorityEntity, Integer> {
}
