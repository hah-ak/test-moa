package my.application.api.services.study.middle.dto;

import java.util.ArrayList;
import java.util.List;

public class AnimalHospital <T extends Animal> {
    private T animal;

    public void setAnimal(T animal) {
        this.animal = animal;
    }

    public T getHead() {
        if (animal == null) {
            return null;
        } else {
            return this.animal;
        }
    }

    public void removeHead() {
        animal = null;
    }

    public boolean bigger(T other) {
        return this.animal.getSize() > other.getSize();
    }
    public <A> A isThatS(A anyThing) {

        return anyThing;
    }

    public static <K> K isThat(K anyThing) {
        return anyThing;
    }

    public static String isThat(AnimalHospital<? extends Cat> anyThing) {
        return anyThing.animal.getName();
    }
    public void animalWildcard(ArrayList<? super Dog> animal) {
        animal.add(null);
    }
}
