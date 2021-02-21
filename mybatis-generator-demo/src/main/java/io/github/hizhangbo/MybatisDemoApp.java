package io.github.hizhangbo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Bob
 * @since 2021/2/17 13:40
 */
@MapperScan("io.github.hizhangbo.dao")
@SpringBootApplication
public class MybatisDemoApp {
    public static void main(String[] args) {
        SpringApplication.run(MybatisDemoApp.class, args);
    }
}
