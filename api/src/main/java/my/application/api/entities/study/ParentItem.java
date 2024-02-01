package my.application.api.entities.study;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // 기본은 단일 테이블이다. 성능이나 편의에 따라 전략설정해야함.
@DiscriminatorColumn // 자식테이블 구분하는 컬럼. 싱글테이블에서는 필수.
public abstract class ParentItem {

    @Id @GeneratedValue
    private Long id;
    private String category;
}
