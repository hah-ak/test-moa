package my.application.api.services.study.middle.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
abstract class Animal {
    private String name;
    private Integer size;

    public String getName() {
        return name;
    }

    public Integer getSize() {
        return size;
    }

}
