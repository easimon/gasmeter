package org.homenet.easimon.smarthome.spring;

import static org.junit.Assert.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.homenet.easimon.smarthome.domain.GasRecordEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SpringContextSmokeTest extends SpringBasedIntegrationTest {

	@Autowired
	private DataSource dataSource;

	@PersistenceContext
	private EntityManager em;

	@Test
	public void testApplicationContext() {
		assertNotNull("Datasource undefined, ApplicationContext incomplete.", dataSource);
	}

	@Test
	public void testEntityRetrieval() {
		em.find(GasRecordEntity.class, -1L);
	}

	@Test
	public void testLocalTime() {
		Instant instant = Instant.now();
		System.out.println(instant);
		LocalDateTime localTime = LocalDateTime.from(instant.atZone(ZoneId.systemDefault()));
		System.out.println(localTime);
	}

}
