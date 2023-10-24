//package com.example;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.HelloWorldApp;
import com.example.JMSMessageReceiver;
import com.example.JMSMessageSender;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApp.class)
public class ActiveMQExampleTest {

@Autowired
private JMSMessageSender sender;

@Autowired
private JMSMessageReceiver receiver;

@Test
public void testReceive() throws Exception {
    sender.send("Sending Message - Example Test");
    receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    assertThat(receiver.getLatch().getCount()).isEqualTo(0);
    }
}