package com.ssafy.trip.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.ssafy.**.dao")
public class Beans {

//	@Bean
//	public Car CCC() {
//		Car car = new Car();
//		car.setTire(kkk());
//		return car;
//	}
//
//	@Bean
//	public Tire kkk() {
//		return new KoreaTire();
//	}
}
