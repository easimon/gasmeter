package org.homenet.easimon.smarthome.spring.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ //
		CommonConfiguration.class, //
		TestPropertySourcesConfiguration.class, //
		DerbyDatabaseConfiguration.class //
})
public class TestConfiguration {

}
