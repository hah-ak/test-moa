package my.application.member.entities.mysql.member;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Set;

@Entity
@Getter
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "RoleCache")
public class RoleEntity {

    @Id
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @OneToMany(mappedBy = "role",fetch = FetchType.EAGER)
    private Set<RolePrivilegeEntity> memberRoleAuthorities;

}
