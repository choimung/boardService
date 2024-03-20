package com.choimung.boardService;

import com.choimung.boardService.config.JdbcConfig;
import com.choimung.boardService.config.MemoryConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@Import(MemoryConfig.class)
@Import(JdbcConfig.class)
@SpringBootApplication(scanBasePackages = {"com.choimung.boardService.service", "com.choimung.boardService.web"})
public class BoardServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardServiceApplication.class, args);
	}

}
