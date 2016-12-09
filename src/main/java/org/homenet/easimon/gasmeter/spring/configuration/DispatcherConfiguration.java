package org.homenet.easimon.gasmeter.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DispatcherConfiguration {

	// darf noch nicht eingelesen werden
	@Bean
	public String throwException() {
		throw new IllegalStateException("Nix is.");
	}

}
