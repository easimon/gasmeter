package org.homenet.easimon.smarthome.domain;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity(name = GasRecordEntity.ENAME)
@Table(name = GasRecordEntity.TNAME, schema = GasRecordEntity.SNAME)
@NamedQueries({ //
        @NamedQuery(name = GasRecordEntity.NQ_INTERVAL, query = GasRecordEntity.NQ_INTERVAL_QUERY), //
        //
})
@NamedNativeQueries({ //
        @NamedNativeQuery( //
        name = GasRecordEntity.NQ_QUANTIZED, //
        query = GasRecordEntity.NQ_QUANTIZED_QUERY, //
        resultSetMapping = "accumulated-gas-record") })
@SqlResultSetMappings({ @SqlResultSetMapping( //
name = "accumulated-gas-record", //
classes = { //
        @ConstructorResult(//
        targetClass = AccumulatedGasRecord.class, //
        columns = { //
                @ColumnResult(name = "ts"), //
                @ColumnResult(name = "amount") //
                }) //
        }) //
})
public class GasRecordEntity implements GasRecord {

    public static final String SNAME = "smarthome";
    public static final String ENAME = "GasRecord";
    public static final String TNAME = "gas";
    private static final String PREFIX = ENAME + ".";

    public static final String NQ_INTERVAL = PREFIX + "Interval";
    public static final String NQ_QUANTIZED = PREFIX + "Quantized";
    static final String NQ_INTERVAL_QUERY = "FROM " + ENAME + " g WHERE g.timestamp BETWEEN :start AND :end ORDER BY g.timestamp";
    static final String NQ_QUANTIZED_QUERY = "SELECT g.ts / :quantizer * :quantizer as ts, sum(g.amount) as amount from " + SNAME + "." + TNAME
            + " g WHERE g.ts BETWEEN :start AND :end GROUP BY g.ts / :quantizer * :quantizer ORDER BY g.ts / :quantizer * :quantizer";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "ts")
    @Temporal(TemporalType.TIMESTAMP)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime timestamp = null;

    @Column(name = "amount")
    private long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "correction")
    private Correction correction;

    protected GasRecordEntity() {
    }

    public long getId() {
        return id;
    }

    @Override
    public DateTime getTimestamp() {
        return this.timestamp;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    public Correction getCorrection() {
        return correction;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", this.getId()).append("timestamp", this.getTimestamp()).append("amount", this.getAmount())
                .append("correction", this.getCorrection()).toString();
    }
}
