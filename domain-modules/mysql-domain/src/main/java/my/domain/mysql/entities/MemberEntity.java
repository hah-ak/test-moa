package my.domain.mysql.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Entity(name = "member")
@Getter
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memNo;
    @Column(unique = true, nullable = false)
    private String id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    private String imageName;
    @Column(nullable = false)
    private String token;

    protected MemberEntity() {

    }
    @Builder
    public MemberEntity(String id, String name, String password, String imageName, String token) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.imageName = imageName;
        this.token = token;
    }
}
