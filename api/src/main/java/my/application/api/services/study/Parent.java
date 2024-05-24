package my.application.api.services.study;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Parent {

    @AllArgsConstructor
    public class NewObjects {
        @Getter
        private int objs;
        public NewObjects changeObjs(int value) {
            return new NewObjects(objs + value);
        }
    }
    @Getter
    @AllArgsConstructor
    public class NewPObjects {
        private int id;
        private NewObjects newObjects;
    }
    public void print() {
        System.out.println("parent");
    }

    public void aaaa() {
        NewPObjects newPObjects = new NewPObjects(1, new NewObjects(1234));
        NewObjects newObjects = newPObjects.getNewObjects().changeObjs(20);
        boolean abs = "abs".equalsIgnoreCase("ABS");
        if (Integer.valueOf(4242) == 4242) {

        }
    }
}
