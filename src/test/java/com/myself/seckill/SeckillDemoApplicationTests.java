package com.myself.seckill;

import com.myself.seckill.utils.ValidatorUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SeckillDemoApplicationTests {

    @Test
    void contextLoads() {
        boolean mobile = ValidatorUtil.isMobile("16608059740");
        System.out.println("～～～～～～～～～～～"+mobile);
    }

}
