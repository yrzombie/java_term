package com.labwork.planning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class PlanningApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlanningApplication.class, args);
    }
}


