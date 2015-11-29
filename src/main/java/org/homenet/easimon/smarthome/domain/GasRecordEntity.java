package org.homenet.easimon.smarthome.domain;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "gas", schema = "smarthome")
@NamedQueries(@NamedQuery(name = GasRecordEntity.NQ_INTERVAL, query = GasRecordEntity.NQ_INTERVAL_QUERY))
public class GasRecordEntity implements GasRecord {
    
    private static final String CLASSNAME = "GasRecord" + ".";
    
    public static final String NQ_INTERVAL = CLASSNAME + "Interval";
    protected static final String NQ_INTERVAL_QUERY = "from GasRecord g where g.timestamp between :start and :end order by g.timestamp";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "ts")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp = null;

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
    public Date getTimestamp() {
        // make unmodifiable
        return new Date(this.timestamp.getTime());
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
