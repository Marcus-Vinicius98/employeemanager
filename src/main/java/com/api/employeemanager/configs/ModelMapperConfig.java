package com.api.employeemanager.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ModelMapperConfig {

	
	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}
	
}
