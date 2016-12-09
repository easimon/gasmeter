package org.homenet.easimon.smarthome.domain;

import java.time.Instant;

import javax.persistence.Column;
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
// @NamedQueries({ //
// @NamedQuery(name = GasRecordEntity.NQ_INTERVAL, query =
// GasRecordEntity.NQ_INTERVAL_QUERY), //
// //
// })
// @NamedNativeQueries({ //
// @NamedNativeQuery( //
// name = GasRecordEntity.NQ_QUANTIZED, //
// query = GasRecordEntity.NQ_QUANTIZED_QUERY, //
// resultSetMapping = "accumulated-gas-record") })
// @SqlResultSetMappings({ @SqlResultSetMapping( //
// name = "accumulated-gas-record", //
// classes = { //
// @ConstructorResult(//
// targetClass = AccumulatedGasRecord.class, //
// columns = { //
// @ColumnResult(name = "ts"), //
// @ColumnResult(name = "amount") //
// }) //
// }) //
// })
public class GasRecordEntity implements GasRecord {

	public static final String ENAME = "GasRecordImpl";
	public static final String TNAME = "gas";
	private static final String PREFIX = ENAME + ".";

	public static final String NQ_ALL = PREFIX + "All";
	public static final String NQ_INTERVAL = PREFIX + "Interval";
	// public static final String NQ_QUANTIZED = PREFIX + "Quantized";
	protected static final String NQ_ALL_QUERY = "FROM " + ENAME + " g ORDER BY g.timestamp";
	protected static final String NQ_INTERVAL_QUERY = "FROM " + ENAME
			+ " g WHERE g.timestamp >= :start AND g.timestamp < :end ORDER BY g.timestamp";
	// static final String NQ_QUANTIZED_QUERY = "SELECT g.ts / :quantizer *
	// :quantizer as ts, sum(g.amount) as amount from "
	// + SNAME + "." + TNAME
	// + " g WHERE g.ts BETWEEN :start AND :end GROUP BY g.ts / :quantizer *
	// :quantizer ORDER BY g.ts / :quantizer * :quantizer";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "ts")
	private long timestamp;

	@Column(name = "amount")
	private long amount;

	@Enumerated(EnumType.STRING)
	@Column(name = "recordtype")
	private GasRecordType type;

	protected GasRecordEntity() {
	}

	public long getId() {
		return id;
	}

	@Override
	public Instant getTimestamp() {
		return Instant.ofEpochSecond(this.timestamp);
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
	public String toString() {
		return new ToStringBuilder(this) //
				.append("id", this.getId()) //
				.append("timestamp", this.getTimestamp()) //
				.append("amount", this.getAmount()) //
				.append("correction", this.getType()) //
				.toString();
	}
}
