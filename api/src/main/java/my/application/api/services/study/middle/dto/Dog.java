package my.application.api.services.study.middle.dto;

public class Dog extends Animal{

    public Dog(String name, Integer size) {
        super(name, size);
    }

    public void bite() {
        System.out.println("DOG");
    }
}
