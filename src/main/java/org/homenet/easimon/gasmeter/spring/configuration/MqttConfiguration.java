package org.homenet.easimon.gasmeter.spring.configuration;

import org.homenet.easimon.gasmeter.integration.mqtt.MqttRouter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.StringUtils;

@Configuration
public class MqttConfiguration {

	@Value("${mqtt.hostname}")
	public String mqttHostname;

	@Value("${mqtt.username}")
	public String mqttUsername;

	@Value("${mqtt.password}")
	public String mqttPassword;

	@Bean
	public MqttPahoClientFactory mqttClientFactory() {
		final DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		factory.setServerURIs("tcp://" + mqttHostname);
		if (!StringUtils.isEmpty(mqttUsername))
			factory.setUserName(mqttUsername);
		if (!StringUtils.isEmpty(mqttPassword))
			factory.setPassword(mqttPassword);
		return factory;
	}

	@Bean
	public MessageChannel mqttInputChannel() {
		return new DirectChannel();
	}

	@Bean
	public MessageProducer inbound(MqttPahoClientFactory clientFactory, MessageChannel mqttInputChannel) {
		final MqttPahoMessageDrivenChannelAdapter adapter = //
				new MqttPahoMessageDrivenChannelAdapter("in", clientFactory);
		adapter.setCompletionTimeout(5000);
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.addTopic("smarthome/#", 2);
		adapter.setOutputChannel(mqttInputChannel);
		return adapter;
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttInputChannel")
	public MqttRouter mqttRouter() {
		return new MqttRouter();
	}
}
