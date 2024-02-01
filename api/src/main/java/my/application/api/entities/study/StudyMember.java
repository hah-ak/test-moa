package my.application.api.entities.study;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
//    @Embedded // 임베디드값 사용. 없어도 됨
    private StudyEmbeddedPeriod insPeriod;
    @Embedded // 중복된 경우 사용
    @AttributeOverrides({
            @AttributeOverride(name = "startDate", column = @Column(name = "upd_start_date")),
            @AttributeOverride(name = "endDate", column = @Column(name = "upd_end_date"))
    })
    private StudyEmbeddedPeriod updPeriod;
    // 값 타입 컬렉션 (값 추적이 안되서 안씀)
    // 컬렉션을 rdb에서 관리하기위해 테이블이 필요하다보니 테이블을 만드는게 필수.(딜리미터 사용하는 방식등의 효율을 위한 실질적 방식 제외)
    // 라이프 사이클은 상위 테이블에 전적으로 의존
    // 이 역시 다른 테이블에서 불러오니 지연로딩.(이미 디폴트)
    // 값을 지우고 추가시 where로 연관된 모든 데이터 지우고 데이터를 모두 다시 끼움
//    @ElementCollection(fetch = FetchType.LAZY)
//    @CollectionTable(name = "prefer_menu",
//            joinColumns = @JoinColumn(name = "id")
//    )
//    private Set<String> preferMenu = new HashSet<>();
//
//    @ElementCollection
//    @CollectionTable(name = "update_history", // 임베디드와 같은 모양의 전혀 관계 없는 테이블만듦.
//        joinColumns = @JoinColumn(name = "id")
//    )
//    private List<StudyEmbeddedPeriod> updatePeriodHistory = new ArrayList<>();

}
