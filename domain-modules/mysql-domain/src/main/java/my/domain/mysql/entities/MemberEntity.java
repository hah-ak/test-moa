package my.domain.mysql.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    private String imageName;
    @CreatedDate
    private LocalDateTime createDateTime;
    @DateTimeFormat
    private LocalDateTime passwordUpdateDateTime;
    private boolean suspended;

    protected MemberEntity() {

    }
    @Builder
    public MemberEntity(String id, String name, String password, String imageName, boolean suspended) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.imageName = imageName;
        this.suspended = suspended;
    }

    @Builder
    public MemberEntity(String id, String name, String password, String imageName) {
        this(id, name, password, imageName, false);
    }
}
