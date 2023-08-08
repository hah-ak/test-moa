package my.application.api.dto.member;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUp {

    private String id;
    private String password;
    private String name;
}
