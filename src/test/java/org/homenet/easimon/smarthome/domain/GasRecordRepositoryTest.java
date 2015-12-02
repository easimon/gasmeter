package org.homenet.easimon.smarthome.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.homenet.easimon.smarthome.spring.SpringBasedIntegrationTest;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GasRecordRepositoryTest extends SpringBasedIntegrationTest {

    @Autowired
    private GasRecordRepository repository;

    @Autowired
    private DataSource dataSource;

    private DateTime getStartDate() {
        return new DateTime(2015, 1, 1, 0, 0, 0);
    }

    private DateTime getEndDate() {
        return new DateTime(2015, 1, 31, 23, 59, 59);
    }

    @Test
    public void testDatasource() throws SQLException {
        this.dataSource.getConnection().prepareStatement("values 1").executeQuery();
    }

    @Test
    public void testGetInterval() {
        List<? extends GasRecord> records = repository.getRecordsForInterval(getStartDate(), getEndDate());
        assertThat(records.size(), is(equalTo(7084)));
        System.out.println(records.size());
    }

    @Test
    public void testGetQuantized() {
        List<? extends GasRecord> records = repository.getAccumulatedGasRecords(getStartDate(), getEndDate(), 500);
        for (GasRecord g : records) {
            System.out.println(g);
        }
    }

}
