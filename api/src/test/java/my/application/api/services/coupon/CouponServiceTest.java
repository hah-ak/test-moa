package my.application.api.services.coupon;

import my.application.api.repositories.redis.CouponRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TestPropertySource("classpath:application-dev.properties")
@SpringBootTest
class CouponServiceTest {

    @Autowired
    CouponService couponService;

    @Autowired
    CouponRepository couponRepository;
    @Test
    void 응모() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(30);
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            final long a = i;
            executorService.submit(() -> {
                couponService.issue(a);
                countDownLatch.countDown();
            });

        }
        countDownLatch.await();

        Assertions.assertThat(couponRepository.count()).isEqualTo(100);
    }

}