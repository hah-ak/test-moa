package my.application.api.entities.study;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Objects;

@EqualsAndHashCode // 임베디드를 포함한 값타입들은 객체주소가 아닌 실제 값으로 동등성을 비교하는게 개념적으로 알맞음.
@Embeddable //  임베디드타입 선언
public class StudyEmbeddedPeriod {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // 임베디드 타입안에엔티티가 있을 수도있음.
    // 임베디드 타입은 위임되는 타입이고 객체이므로 레퍼런스로 동작하니 값 변경시 commit때 값이 다바뀌어서 문제가 생길수있음.
    // 객체자체를 바꿔서 넣는게 안전함.


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        StudyEmbeddedPeriod that = (StudyEmbeddedPeriod) o;
//        return Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(startDate, endDate);
//    }
}
