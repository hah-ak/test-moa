package my.application.api.entities.study;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name"},name = "studyUnique")}) // 컬럼에 unique쓰면 유니크 키에 랜덤한 이름이 부여됨. 그래서 여기에 쓰는게 좋다함
//@SequenceGenerator(name = "study_seq_generator",sequenceName = "study_seq",initialValue = 1, allocationSize = 2)//시퀀스 지정
public class StudyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "study_seq_generator")
    private Long id;
    @Column(updatable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    private ROLE role;
//    @Temporal(TemporalType.TIMESTAMP) local date time 같은거 쓰면 노필요.
    private LocalDateTime createTIme;
    @Lob // blob clob 타입
    private String description;
    @Transient
    private String onlyMemoryInfo;

}
