package com.example;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@SpringBootConfiguration
//@Configuration
@EnableJms
public class JMSMessageReceiverConfig {

    @Value("${activemq.broker-url}")
    private String brokerUrl;

    //private String brokerUrl = "localhost"; //need to fix back on .properties parsing


    @Bean
    public ActiveMQConnectionFactory receiverActiveMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        return activeMQConnectionFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory =
            new DefaultJmsListenerContainerFactory();
        factory
            .setConnectionFactory(receiverActiveMQConnectionFactory());
        return factory;
    }

    @Bean
    public JMSMessageReceiver receiver() {
        return new JMSMessageReceiver();
    }

    @Bean
    public JMSMessageSender sender() {
        return new JMSMessageSender();
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(receiverActiveMQConnectionFactory());
    }
}