package org.homenet.easimon.gasmeter.spring.configuration;

import org.homenet.easimon.gasmeter.spring.configuration.CommonConfiguration;
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
