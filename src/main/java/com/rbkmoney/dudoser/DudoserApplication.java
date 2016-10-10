package com.rbkmoney.dudoser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.rbkmoney.dudoser"})
public class DudoserApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(DudoserApplication.class, args);
    }
}
