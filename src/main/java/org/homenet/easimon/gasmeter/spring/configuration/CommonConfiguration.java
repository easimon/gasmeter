package org.homenet.easimon.gasmeter.spring.configuration;

import java.time.ZoneId;
import java.util.Locale;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { //
		CommonConfiguration.BASE_PACKAGE_ASPECTS, //
		CommonConfiguration.BASE_PACKAGE_DOMAIN, //
		CommonConfiguration.BASE_PACKAGE_INTEGRATION, //
		CommonConfiguration.BASE_PACKAGE_CONTROLLER //
})
public class CommonConfiguration {

	public static final String BASE_PACKAGE_APPLICATION = "org.homenet.easimon.gasmeter.";
	public static final String BASE_PACKAGE_INTEGRATION = BASE_PACKAGE_APPLICATION + "integration";
	public static final String BASE_PACKAGE_DOMAIN = BASE_PACKAGE_APPLICATION + "domain";
	public static final String BASE_PACKAGE_CONTROLLER = BASE_PACKAGE_APPLICATION + "controller";
	public static final String BASE_PACKAGE_ASPECTS = BASE_PACKAGE_APPLICATION + "aspects";

	// TODO: Session Bean
	@Bean
	public ZoneId displayZoneId() {
		return ZoneId.of("Europe/Berlin");
	}

	// TODO: Session Bean.
	@Bean
	public Locale displayLocale() {
		return Locale.GERMANY;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public PlatformTransactionManager txManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
			JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
		lef.setDataSource(dataSource);
		lef.setJpaVendorAdapter(jpaVendorAdapter);
		lef.setPackagesToScan(BASE_PACKAGE_DOMAIN);
		return lef;
	}

}
