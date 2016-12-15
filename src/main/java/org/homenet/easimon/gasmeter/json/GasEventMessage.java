package org.homenet.easimon.gasmeter.json;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.homenet.easimon.gasmeter.domain.GasRecordType;

@XmlRootElement
public class GasEventMessage {

	private GasRecordType type;
	private long amount;
	private String clientId;

	public GasEventMessage() {
	}

	public GasRecordType getType() {
		return type;
	}

	public void setType(GasRecordType type) {
		this.type = type;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this) //
				.append("amount", amount) //
				.append("type", type) //
				.append("clientId", clientId) //
				.toString();
	}
}
