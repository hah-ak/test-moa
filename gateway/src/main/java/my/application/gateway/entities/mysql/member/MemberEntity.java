package my.application.gateway.entities.mysql.member;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    private Set<MemberRoleEntity> roles = new HashSet<>();
    @CreatedDate
    private LocalDateTime createDateTime;
    @DateTimeFormat
    private LocalDateTime passwordUpdateDateTime;
    private boolean suspended; //정지

    protected MemberEntity() {

    }
    @Builder
    public MemberEntity(String id, String name, String password, String imageName, boolean suspended, Set<MemberRoleEntity> roles) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.imageName = imageName;
        this.suspended = suspended;
        this.roles = roles;
    }

    @Builder
    public MemberEntity(String id, String name, String password, String imageName, Set<MemberRoleEntity> roles) {
        this(id, name, password, imageName, false, roles);
    }
}
