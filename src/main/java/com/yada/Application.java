package com.yada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
//	public static void main(String[] args) {
//		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
//
//		System.out.println(applicationContext.getBean("test"));
////		for (String name : applicationContext.getBeanDefinitionNames()) {
////			System.out.println(name);
////		}
//	}
}