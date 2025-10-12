package com.example.educonnect;

import org.springframework.boot.SpringApplication;

public class TestEduConnectApplication {

    public static void main(String[] args) {
        SpringApplication.from(EduConnectApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
