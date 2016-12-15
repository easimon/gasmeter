package org.homenet.easimon.gasmeter.domain;

import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public GasRecord createNormalGasRecord(Instant timestamp) {
		long amount = 1;
		GasRecordType type = GasRecordType.NORMAL;
		GasRecord record = new GasRecordEntity(timestamp, amount, type);
		em.persist(record);
		return record;
	}

	@Transactional
	public GasRecord createCorrectionGasRecord(Instant timestamp, long amount) {
		GasRecordType type = GasRecordType.CORRECTION;
		GasRecord record = new GasRecordEntity(timestamp, amount, type);
		em.persist(record);
		return record;
	}

}
