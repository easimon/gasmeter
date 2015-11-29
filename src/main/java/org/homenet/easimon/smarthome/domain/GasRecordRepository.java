package org.homenet.easimon.smarthome.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class GasRecordRepository {

    @PersistenceContext
    private EntityManager em;

    public List<GasRecordEntity> getRecordsForInterval(Date from, Date to) {
        TypedQuery<GasRecordEntity> q = em.createNamedQuery(GasRecordEntity.NQ_INTERVAL, GasRecordEntity.class);
        q.setParameter("start", from);
        q.setParameter("end", to);
        return q.getResultList();
    }
    
    private Date getDayStart(Date date) {
        Calendar result = Calendar.getInstance();
        result.setTime(date);
        result.clear(Calendar.HOUR_OF_DAY);
        result.clear(Calendar.MINUTE);
        result.clear(Calendar.SECOND);
        result.clear(Calendar.MILLISECOND);
        return result.getTime();
    }    
    
    public List<? extends GasRecord> getRecordsForIntervalDaily(Date from, Date to) {
        List<? extends GasRecord> all = getRecordsForInterval(from, to);  
        
        Date dayStart = null;
        
        for (GasRecord record: all) {
            if (!getDayStart(record.getTimestamp() ).equals(dayStart)) {
                // TODO dayStart = 
            }
        }
        
        return all;
    }

}
