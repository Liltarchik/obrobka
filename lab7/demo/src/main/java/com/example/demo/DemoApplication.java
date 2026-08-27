package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        System.out.println("\n\n==========================================");
        System.out.println("====== ЗАПУЩЕНО ЛАБОРАТОРНУ №7 ======");
        System.out.println("==========================================\n\n");
        SpringApplication.run(DemoApplication.class, args);
    }
}