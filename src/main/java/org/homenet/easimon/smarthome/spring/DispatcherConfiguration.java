package org.homenet.easimon.smarthome.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources(@PropertySource(value = { "classpath:jdbc.properties" }))
public class DispatcherConfiguration {

}
