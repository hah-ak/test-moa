package my.application.gateway.entities.mysql.member;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class MemberRoleEntity {

    @Id
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @OneToMany(targetEntity = MemberRoleAuthorityEntity.class, mappedBy = "memberRole")
    private Set<MemberRoleAuthorityEntity> memberRoleAuthorities;
}
