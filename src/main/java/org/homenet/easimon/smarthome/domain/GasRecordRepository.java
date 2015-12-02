package org.homenet.easimon.smarthome.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

@Repository
public class GasRecordRepository {

    @PersistenceContext
    private EntityManager em;

    public List<GasRecordEntity> getRecordsForInterval(DateTime from, DateTime to) {
        TypedQuery<GasRecordEntity> q = em.createNamedQuery(GasRecordEntity.NQ_INTERVAL, GasRecordEntity.class);
        q.setParameter("start", from);
        q.setParameter("end", to);
        return q.getResultList();
    }

    public List<? extends GasRecord> getRecordsForIntervalDaily(DateTime from, DateTime to) {
        List<? extends GasRecord> all = getRecordsForInterval(from, to);

        // Date dayStart = null;

        // for (GasRecord record : all) {
        // if (!getDayStart(record.getTimestamp()).equals(dayStart)) {
        // // TODO dayStart =
        // }
        // }

        return all;
    }

    public List<? extends GasRecord> getAccumulatedGasRecords(DateTime from, DateTime to, long quantizerSeconds) {
        List<? extends GasRecord> results = em.createNamedQuery(GasRecordEntity.NQ_QUANTIZED, AccumulatedGasRecord.class) //
                .setParameter("quantizer", quantizerSeconds) //
                .setParameter("start", from) //
                .setParameter("end", to) //
                .getResultList();
        return results;
    }

}
