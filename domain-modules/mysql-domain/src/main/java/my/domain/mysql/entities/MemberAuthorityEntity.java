package my.domain.mysql.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity(name="authority")
public class MemberAuthorityEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int no;
    @ManyToOne(targetEntity = MemberEntity.class)
    @JoinColumn(name = "memNo", nullable = false)
    private MemberEntity member;
    @Column(name = "authority")
    private String authority;

}
