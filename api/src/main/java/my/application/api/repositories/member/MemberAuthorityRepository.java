package my.application.api.repositories.member;

import my.application.api.entities.MemberAuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAuthorityRepository extends JpaRepository<MemberAuthorityEntity, Integer> {
}
