package org.homenet.easimon.gasmeter.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity(name = GasRecordEntity.ENAME)
@Table(name = GasRecordEntity.TNAME)
@NamedQueries({ //
		@NamedQuery(name = GasRecordEntity.NQ_ALL, query = GasRecordEntity.NQ_ALL_QUERY), //
		@NamedQuery(name = GasRecordEntity.NQ_INTERVAL, query = GasRecordEntity.NQ_INTERVAL_QUERY), //
})
public class GasRecordEntity implements GasRecord {

	public static final String ENAME = "GasRecordImpl";
	public static final String TNAME = "gas";
	private static final String PREFIX = ENAME + ".";

	public static final String NQ_ALL = PREFIX + "All";
	public static final String NQ_INTERVAL = PREFIX + "Interval";
	protected static final String NQ_ALL_QUERY = "FROM " + ENAME + " g ORDER BY g.timestamp";
	protected static final String NQ_INTERVAL_QUERY = "FROM " + ENAME
			+ " g WHERE g.timestamp >= :start AND g.timestamp < :end ORDER BY g.timestamp";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "ts")
	@Convert(converter = InstantToEpochSecondConverter.class)
	private Instant timestamp;

	@Column(name = "amount")
	private long amount;

	@Enumerated(EnumType.STRING)
	@Column(name = "recordtype")
	private GasRecordType type;

	protected GasRecordEntity() {
	}

	public GasRecordEntity(Instant timestamp, long amount, GasRecordType type) {
		this.timestamp = timestamp;
		this.amount = amount;
		this.type = type;
	}

	public long getId() {
		return id;
	}

	@Override
	public long getAmount() {
		return amount;
	}

	@Override
	public GasRecordType getType() {
		return type;
	}

	@Override
	public Instant getTimestamp() {
		return this.timestamp;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this) //
				.append("id", this.getId()) //
				.append("timestamp", this.getTimestamp()) //
				.append("amount", this.getAmount()) //
				.append("correction", this.getType()) //
				.toString();
	}
}
