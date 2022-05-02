package com.yoon.twicePractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //AuditingEntityListener 활성화
public class TwicePracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwicePracticeApplication.class, args);
	}

}
