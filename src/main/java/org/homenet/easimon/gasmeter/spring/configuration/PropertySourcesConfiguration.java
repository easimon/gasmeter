package org.homenet.easimon.gasmeter.spring.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(ignoreResourceNotFound = false, value = "spring.properties")
public class PropertySourcesConfiguration {

}
