package org.homenet.easimon.smarthome.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.homenet.easimon.smarthome.spring.SpringBasedIntegrationTest;
import org.homenet.easimon.smarthome.util.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GasRecordRepositoryTest extends SpringBasedIntegrationTest {

    @Autowired
    private GasRecordRepository repository;


    private Date getStartDate() {
        return DateUtils.getDate(2015, Calendar.JANUARY, 1);
    }

    private Date getEndDate() {
        return DateUtils.getDate(2015, Calendar.JANUARY, 31, 23, 59, 59);
    }

    @Test
    public void testGetInterval() {
        List<? extends GasRecord> records = repository.getRecordsForInterval(getStartDate(), getEndDate());
        System.out.println(records.size());        
    }

    @Test
    public void testGetInterval2() {
        List<? extends GasRecord> records = repository.getRecordsForInterval(getStartDate(), getEndDate());
        System.out.println(records.size());        
    }

}
