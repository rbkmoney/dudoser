package com.rbkmoney.dudoser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication(scanBasePackages = {"com.rbkmoney.dudoser", "com.rbkmoney.dbinit"})
public class DudoserApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(DudoserApplication.class, args);
    }
}
