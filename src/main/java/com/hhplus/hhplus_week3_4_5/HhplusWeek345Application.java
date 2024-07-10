package com.hhplus.hhplus_week3_4_5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HhplusWeek345Application {

    public static void main(String[] args) {
        SpringApplication.run(HhplusWeek345Application.class, args);
    }

}
