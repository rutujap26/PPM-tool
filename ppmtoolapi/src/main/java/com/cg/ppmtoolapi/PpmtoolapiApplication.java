package com.cg.ppmtoolapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@CrossOrigin
public class PpmtoolapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PpmtoolapiApplication.class, args);
	}

}
