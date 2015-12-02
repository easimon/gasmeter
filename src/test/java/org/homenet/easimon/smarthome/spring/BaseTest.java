package org.homenet.easimon.smarthome.spring;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.homenet.easimon.smarthome.domain.GasRecordEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseTest extends SpringBasedIntegrationTest {

    @Autowired
    private DataSource dataSource;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void test() {
        assertNotNull(dataSource);
    }

    @Test
    public void testEntity() {
        GasRecordEntity record = em.find(GasRecordEntity.class, 1L);
        assertNotNull(record);
        // System.out.println(record);
    }

}
