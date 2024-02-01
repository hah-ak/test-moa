package my.application.api.services.study;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Objects;

public class HashTest {

    @Getter
    @AllArgsConstructor
    public static class TestType {
        private LocalDateTime startDate;
        private LocalDateTime endDate;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestType testType = (TestType) o;
            return Objects.equals(startDate, testType.startDate) && Objects.equals(endDate, testType.endDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate);
        }
    }

    @Test
    void hashTest() {
        LocalDateTime now = LocalDateTime.now();
        TestType testType = new TestType(now, now);
        TestType testType1 = new TestType(now, now);

        System.out.printf("testType : %d,   testType1: %d\n",testType.hashCode(), testType1.hashCode());
        System.out.println(testType);
        System.out.println(testType1);
        System.out.println(System.identityHashCode(testType));
        System.out.println(System.identityHashCode(testType1));
    }
}
