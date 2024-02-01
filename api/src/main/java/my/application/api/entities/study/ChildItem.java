package my.application.api.entities.study;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
//@DiscriminatorValue("C") DiscriminatorColumn 에 들어갈 값의 형태를 따로 지정해주는 경우 사용.
public class ChildItem extends ParentItem {
    // id값은 부모와 같은 값으로 만들어지게 된다. (pk 이자 fk)
    private String detail;
}
