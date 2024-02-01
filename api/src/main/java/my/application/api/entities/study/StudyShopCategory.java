package my.application.api.entities.study;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class StudyShopCategory {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    //중간테이블 생성
    @JoinTable(name = "CATEGORY_ITEM", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<StudyShopItem> items = new ArrayList<>(); // 널 제거를 위한 기본값 셋팅
    // 셀프 조인.
    @ManyToOne(fetch = FetchType.LAZY) // 정말 사용을 거의 동시에 하거나 사용량이 많다면, eager로 즉시 로딩.
    @JoinColumn(name = "parent_id")
    // eager는 join을 사용해서 바로 가져옴 그러나 문제가있음. 데이터가 커지면 조인이 엄청나게 많아짐.
    // jpql같은 경우 쿼리를 직접 번역하다보니 위와같은 문제가 많이 발생할 수 있음. + N+1문제발생.
    private StudyShopCategory parent;
    @OneToMany(mappedBy = "parent")
    private List<StudyShopCategory> child;
}
