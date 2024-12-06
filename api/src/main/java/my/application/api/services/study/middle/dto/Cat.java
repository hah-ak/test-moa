package my.application.api.services.study.middle.dto;

public class Cat extends Animal{

    public Cat(String name, Integer size) {
        super(name, size);
    }

    public void meow() {
        System.out.println("CAT");
    }
}
