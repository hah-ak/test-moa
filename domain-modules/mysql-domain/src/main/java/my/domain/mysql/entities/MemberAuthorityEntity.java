package my.domain.mysql.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity(name="authority")
public class MemberAuthorityEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int no;
    @ManyToOne(targetEntity = MemberEntity.class)
    private MemberEntity member;
    @Column(name = "authority")
    private String authority;

}
