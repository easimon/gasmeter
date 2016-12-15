package org.homenet.easimon.gasmeter.integration.mqtt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
public class MqttFallbackHandler implements MessageHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(MqttFallbackHandler.class);

	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		LOGGER.warn("Received unknown message on topic {}, payload: {}", message.getHeaders().get("mqtt_topic"),
				message.getPayload());

	}

}
