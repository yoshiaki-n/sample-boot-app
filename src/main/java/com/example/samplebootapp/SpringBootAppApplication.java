package com.example.samplebootapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** アプリケーションのメインクラスです。 */
@SpringBootApplication
@MapperScan("com.example.samplebootapp.infrastructure")
public class SpringBootAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootAppApplication.class, args);
  }
}
