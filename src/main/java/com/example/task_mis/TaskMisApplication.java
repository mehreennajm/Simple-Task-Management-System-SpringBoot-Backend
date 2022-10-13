package com.example.task_mis;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



@SpringBootApplication

public class TaskMisApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}



	public static void main(String[] args) {
		SpringApplication.run(TaskMisApplication.class, args);
	}

}
