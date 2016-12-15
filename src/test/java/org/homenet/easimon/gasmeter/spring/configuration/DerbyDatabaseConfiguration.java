package org.homenet.easimon.gasmeter.spring.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class DerbyDatabaseConfiguration {

	@Value("${db.ddl.init.ddl}")
	public String initializerDdl;

	@Value("${db.ddl.init.sql}")
	public String initializerSql;

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder() //
				.addScript(initializerDdl) //
//				.addScript(initializerSql) //
				.setType(EmbeddedDatabaseType.DERBY) //
				.generateUniqueName(true) //
				.build();
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(false);
		hibernateJpaVendorAdapter.setGenerateDdl(false);
		hibernateJpaVendorAdapter.setDatabase(Database.DERBY);
		return hibernateJpaVendorAdapter;
	}
}
