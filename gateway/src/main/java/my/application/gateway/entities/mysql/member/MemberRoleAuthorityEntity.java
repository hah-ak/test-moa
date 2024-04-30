package my.application.gateway.entities.mysql.member;

import jakarta.persistence.*;

@Entity
public class MemberRoleAuthorityEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = MemberAuthorityEntity.class)
    private MemberAuthorityEntity memberAuthority;
    @ManyToOne(targetEntity = MemberRoleEntity.class)
    private MemberRoleEntity memberRole;
}
