package com.org.kafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaWorkflowService {

    @Autowired
    private KafkaProducer kafkaProducer;

    public void triggerStateTransition(Long workflowId, Long fromStateId, Long toStateId) {
        // Send transition message to Kafka
        kafkaProducer.sendTransitionMessage("workflow-transition", workflowId, fromStateId, toStateId);
    }
}
