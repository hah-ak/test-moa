package my.application.api.services.study.middle;

import my.application.api.services.study.middle.dto.AnimalHospital;
import my.application.api.services.study.middle.dto.Cat;
import my.application.api.services.study.middle.dto.Dog;

public class MiddleMain {
    public static void main(String[] args) {
        AnimalHospital<Cat> animalAnimalHospital = new AnimalHospital<>();

        animalAnimalHospital.setAnimal(new Cat("cat",100));

        animalAnimalHospital.bigger(new Cat("dog",200));

        AnimalHospital.isThat(new Dog("a",123));

        animalAnimalHospital.removeHead();

        // 타입이레이져로 컴파일 시점에 전부다 object가 되니까 인스턴스와 직접 관련된 행위(new, instance of)를 제네릭으로 할 수 없다.

    }
}
