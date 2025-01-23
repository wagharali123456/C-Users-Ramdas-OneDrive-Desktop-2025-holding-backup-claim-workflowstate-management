package com.org.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // Send transition request to Kafka
    public void sendTransitionMessage(String topic, Long workflowId, Long fromStateId, Long toStateId) {
        // Construct message (can be in JSON format)
        String message = "{\"workflowId\": " + workflowId + ", \"fromStateId\": " + fromStateId + ", \"toStateId\": " + toStateId + "}";
        
        // Send the message to Kafka topic
        kafkaTemplate.send(topic, message);
    }
}
