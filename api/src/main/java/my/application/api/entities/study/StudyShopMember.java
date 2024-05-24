package my.application.api.entities.study;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class StudyShopMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    public StudyShopMember(
            String name,
            String city,
            String street,
            String zipCode
    ) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }

    private String name;
    private String city;
    private String street;
    private String zipCode;



    public StudyShopMember() {

    }

    // 객체에서 단방향, 양방향 관계가 있을 수 있음.
    // 양방향 관계에서 mappedBy를 써야함. (fk가 있는 곳)
    // 실질적으로 호출횟수가 크게 많거나 특별한 이유가 없다면 최대한 단방향으로 끝내는게 문제없는 서비스 유지에 도움됨.
    // owner 객체에 업데이트를 해줘야 정확히 set을해줘야 db가 업데이트됨.
    // owner 객체가 아닌 곳에서의 수정은 실제 db에 영향을 주지 않는다. 예를들어, one to many에서 list에 add를 하고 set을 해줘도 변화가 생기지 않는다.
    // 오히려 owner 객체가 persist 상태가 아니거나 db에 없이 리스트에 추가해commit flush를 하면 에러발생( 없는데 찾으라고 한거므로)
    // persist context 내부에서 객체지향적 설계를 위해 set을 해주면서 list에 add를 해주는게 좋다. (1차 캐시에서는 업데이트가 안되어있을것이므로)
//    @OneToMany(mappedBy = "member") // mappedBy가 있다면 연관관계의 owner 가 아니다( fk를 가지지 않는다 ) mapped By 는 필드를 명시한다. (one to one도 마찬가지)
//    private List<StudyShopOrder> orders = new ArrayList<>();
    // 다대일 단반향 관계가 가장 비즈니스적으로 쉽다.
    // 일대일 보조테이블의 경우 패치조인을 제대로 하지 않으면 즉시 로딩되어서 문제가 생김.
    // 다대다는 근본적으로 정규화불가. 카테시안곱도 나옴. 보조테이블이 필수이며 그 보조테이블을 엔티티화시켜야 예기치 못한 사고 막을 수 있음.
}
