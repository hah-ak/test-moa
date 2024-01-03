package my.application.api.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

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
    private String role;
    @OneToMany(targetEntity = MemberAuthorityEntity.class)
    private List<MemberAuthorityEntity> authority;
    @CreatedDate
    private LocalDateTime createDateTime;
    @DateTimeFormat
    private LocalDateTime passwordUpdateDateTime;
    private boolean suspended; //정지

    protected MemberEntity() {

    }
    @Builder
    public MemberEntity(String id, String name, String password, String imageName, boolean suspended, String role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.imageName = imageName;
        this.suspended = suspended;
        this.role = role;
    }

    @Builder
    public MemberEntity(String id, String name, String password, String imageName, String role) {
        this(id, name, password, imageName, false, role);
    }
}
