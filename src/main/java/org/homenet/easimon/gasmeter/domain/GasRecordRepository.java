package org.homenet.easimon.gasmeter.domain;

import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class GasRecordRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(GasRecordRepository.class);

	@PersistenceContext
	private EntityManager em;

	public List<GasRecord> findAllGasRecords() {
		return em //
				.createNamedQuery(GasRecordEntity.NQ_ALL, GasRecord.class) //
				.getResultList();
	}

	public List<GasRecord> findGasRecordsByPeriod(TemporalAccessor from, TemporalAccessor to) {
		LOGGER.debug("Selecting all Records >= {} and < {}.", from, to);
		return em //
				.createNamedQuery(GasRecordEntity.NQ_INTERVAL, GasRecord.class) //
				.setParameter("start", Instant.from(from)) //
				.setParameter("end", Instant.from(to)) //
				.getResultList();
	}

}
