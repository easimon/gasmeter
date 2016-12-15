package org.homenet.easimon.gasmeter.integration.mqtt;

import java.io.IOException;
import java.time.Instant;

import org.homenet.easimon.gasmeter.domain.GasRecord;
import org.homenet.easimon.gasmeter.domain.GasRecordRepository;
import org.homenet.easimon.gasmeter.json.GasEventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

@Component
public class MqttGasEventHandler implements MessageHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(MqttGasEventHandler.class);

	@Autowired
	private GasRecordRepository repository;

	private ObjectReader reader;

	@Autowired
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.reader = objectMapper.readerFor(GasEventMessage.class);
	}

	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		Object payload = message.getPayload();
		LOGGER.info("Received message payload: {}", payload);
		if (!(payload instanceof String)) {
			LOGGER.error("Payload {} is not a String.", payload);
			return;
		}
		try {
			GasEventMessage event = reader.readValue(String.valueOf(payload));
			LOGGER.info("Successfully parsed Event: {}", event);
			GasRecord record = repository.createNormalGasRecord(Instant.now());
			LOGGER.info("Successfully created Record: {}", record);
		} catch (IOException e) {
			LOGGER.error("Payload {} could not be parsed.", payload, e);
		}
	}

}
