package my.application.gateway.entities.mysql.member;

import jakarta.persistence.*;

@Entity
public class RolePrivilegeEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private PrivilegeEntity privilege;
    @ManyToOne
    private RoleEntity role;
}
