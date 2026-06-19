package com.mycom.myapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.mycom.myapp.auth.dao", "com.mycom.myapp.user.dao", "com.mycom.myapp.board.dao", "com.mycom.myapp.model.mapper"})
public class StockTalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockTalkApplication.class, args);
	}

}
