package my.application.gateway.entities.mysql.member;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity(name = "member")
@Getter
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memNo;
    @Column(unique = true, nullable = false)
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    private String imageName;
    @Column(nullable = false)
    private MemberRole role;
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private MemberAuthority authority;
    @CreatedDate
    private LocalDateTime createDateTime;
    @DateTimeFormat
    private LocalDateTime passwordUpdateDateTime;
    private boolean suspended; //정지

    protected MemberEntity() {

    }
    @Builder
    public MemberEntity(String id, String name, String password, String imageName, boolean suspended, MemberRole role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.imageName = imageName;
        this.suspended = suspended;
        this.role = role;
    }

    @Builder
    public MemberEntity(String id, String name, String password, String imageName, MemberRole role) {
        this(id, name, password, imageName, false, role);
    }
}
