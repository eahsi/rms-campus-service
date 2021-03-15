package com.revature.rms.campus;

import com.revature.rms.campus.entities.*;
import com.revature.rms.campus.repositories.CampusRepository;
import com.revature.rms.campus.services.BuildingService;
import com.revature.rms.campus.services.CampusService;
import com.revature.rms.campus.services.RoomService;
import com.revature.rms.core.config.EurekaInstanceConfigBeanPostProcessor;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableEurekaClient
@EnableSwagger2
@SpringBootApplication
@OpenAPIDefinition(info =
	@Info(title = "Campus API", version = "1.0", description = "Documentation Campus API v1.0")
)
public class CampusServiceApplication  {

	@Autowired
	private CampusService campusService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private BuildingService buildingService;

	public static void main(String[] args) {

		SpringApplication.run(CampusServiceApplication.class, args);
	}
	@Bean
	public EurekaInstanceConfigBeanPostProcessor eurekaInstanceConfigBeanPostProcessor(){
		return new EurekaInstanceConfigBeanPostProcessor();
	}

	@Bean
	public Docket swaggerPersonApi10() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.revature.rms.campus.controllers"))
					.paths(PathSelectors.any())
				.build()
				.apiInfo(new ApiInfoBuilder().version("1.0").title("Campus API").description("Documentation Campus API v1.0").build());
	}
}
