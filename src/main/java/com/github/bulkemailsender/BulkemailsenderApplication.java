package com.github.bulkemailsender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BulkemailsenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BulkemailsenderApplication.class, args);
    }

}
