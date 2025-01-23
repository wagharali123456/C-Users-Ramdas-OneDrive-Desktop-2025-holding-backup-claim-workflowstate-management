package com.org.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaClaimProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // Send transition request to Kafka for a claim state transition
    public void sendClaimTransitionMessage(String topic, Long claimId, Long fromStateId, Long toStateId) {
        // Constructing JSON message
        String message = "{\"claimId\": " + claimId + ", \"fromStateId\": " + fromStateId + ", \"toStateId\": " + toStateId + "}";
        
        // Send message to the Kafka topic
        kafkaTemplate.send(topic, message);
    }
}
