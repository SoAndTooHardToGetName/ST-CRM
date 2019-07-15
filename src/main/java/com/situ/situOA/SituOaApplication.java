package com.situ.situOA;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@ComponentScan(basePackages = { "com.situ.situOA.*.controller,com.situ.situOA.*.service.impl" })
@MapperScan(value = "com.situ.situOA.*.mapper")
public class SituOaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SituOaApplication.class, args);
	}

}