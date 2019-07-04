package com.rbkmoney.dudoser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@ServletComponentScan
@EnableCaching
@EnableScheduling
@EnableRetry
@SpringBootApplication(scanBasePackages = {"com.rbkmoney.dudoser"})
public class DudoserApplication {
    public static void main(String[] args) {
        SpringApplication.run(DudoserApplication.class, args);
    }
}
