package org.homenet.easimon.gasmeter.integration.mqtt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
// TODO: This is Spring Integration for Idiots. Make it right!
public class MqttRouter implements MessageHandler {

	@Autowired
	private MqttGasEventHandler mqttGasEventHandler;

	@Autowired
	private MqttHeartbeatHandler mqttHeartbeatHandler;

	@Autowired
	private MqttFallbackHandler mqttFallbackHandler;

	private String getTopic(Message<?> message) {
		return Optional.ofNullable(message.getHeaders().get("mqtt_topic", String.class)).orElse(null);
	}

	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		String topic = getTopic(message);
		if (topic == null) {
			mqttFallbackHandler.handleMessage(message);
			return;
		} else if (topic.startsWith("smarthome/gasmeter")) {
			mqttGasEventHandler.handleMessage(message);
			return;
		} else if (topic.startsWith("smarthome/heartbeat")) {
			mqttHeartbeatHandler.handleMessage(message);
			return;
		} else {
			mqttFallbackHandler.handleMessage(message);
		}
	}

}
