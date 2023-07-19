package my.application.api.entities.mysql;

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

    @Builder
    protected MemberEntity() {

    }
}
