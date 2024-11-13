package my.application.user.entities.mysql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import my.application.user.entities.mysql.company.Company;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Getter
@Entity(name = "member")
public class MemberEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memNo;
    @Column(unique = true, nullable = false)
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    private String imageName;
    @CreatedDate
    private LocalDateTime createDateTime;
    @DateTimeFormat
    private LocalDateTime passwordUpdateDateTime;
    private boolean suspended; //정지

    @OneToOne
    @Setter
    private Company company;

    protected MemberEntity() {}

    public MemberEntity(String id, String name, String password, String imageName, boolean suspended) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.imageName = imageName;
        this.suspended = suspended;
    }

    public MemberEntity(String id, String name, String password, String imageName) {
        this(id, name, password, imageName, false);
    }
}
