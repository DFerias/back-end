package br.com.dferias.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableWebSecurity
@Slf4j
public class ApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiApplication.class, args);
    System.out.println("\n\n\n\n\n\n=================================================================");
    System.out.println("||                                                             ||");
    System.out.println("||                        API INICIADA                         ||");
    System.out.println("||                                                             ||");
    System.out.println("=================================================================\n\n\n");
  }
}
