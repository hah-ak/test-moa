package my.application.member.entities.mysql.member;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class MemberRoleEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private MemberEntity member;
    @ManyToOne
    private RoleEntity role;

    public MemberRoleEntity() {}

    public MemberRoleEntity(MemberEntity member, RoleEntity role) {
        this.member = member;
        this.role = role;
    }
}
