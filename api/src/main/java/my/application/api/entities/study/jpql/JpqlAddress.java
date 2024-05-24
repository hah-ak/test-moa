package my.application.api.entities.study.jpql;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable
@Getter
@AllArgsConstructor
public class JpqlAddress {
    private String city;
    private String street;
    private String zipCode;

    public JpqlAddress() {

    }
}
