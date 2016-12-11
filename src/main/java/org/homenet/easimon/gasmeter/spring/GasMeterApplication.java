package org.homenet.easimon.gasmeter.spring;

import java.util.Arrays;

import org.homenet.easimon.gasmeter.spring.configuration.GasMeterConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAutoConfiguration
@Import(GasMeterConfiguration.class)
public class GasMeterApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(GasMeterApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GasMeterApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			LOGGER.info("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				Object bean = ctx.getBean(beanName);
				// if
				// (bean.getClass().getName().startsWith("org.homenet.easimon"))
				LOGGER.info("{}: {}", beanName, bean.getClass());
			}

		};
	}
}
