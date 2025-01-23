package com.org.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.fms.model.State;
import com.org.fms.model.Workflow;
import com.org.fms.repository.StateRepository;
import com.org.fms.repository.TransitionRepository;
import com.org.fms.repository.WorkflowRepository;
import com.org.kafka.model.TransitionMessage;

@Service
public class KafkaWorkflowConsumer {

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private TransitionRepository transitionRepository;

    // Kafka Listener to listen to 'workflow-transition' topic
    @KafkaListener(topics = "workflow-transition", groupId = "workflow-consumer-group")
    public void listenToWorkflowTransition(String message) {
        System.out.println("Received Kafka Message: " + message);

        try {
            // Deserialize message to TransitionMessage object
            ObjectMapper objectMapper = new ObjectMapper();
            TransitionMessage transitionMessage = objectMapper.readValue(message, TransitionMessage.class);

            // Fetch workflow by ID
            Workflow workflow = workflowRepository.findById(transitionMessage.getWorkflowId())
                    .orElseThrow(() -> new RuntimeException("Workflow not found"));

            // Fetch the current and next states by IDs
            State fromState = stateRepository.findById(transitionMessage.getFromStateId())
                    .orElseThrow(() -> new RuntimeException("From State not found"));

            State toState = stateRepository.findById(transitionMessage.getToStateId())
                    .orElseThrow(() -> new RuntimeException("To State not found"));

            // Validate and perform the transition (can be extended as needed)
            if (isTransitionValid(fromState, toState)) {
                System.out.println("Transitioning state from " + fromState.getName() + " to " + toState.getName());
                // You can add custom logic to update the state of the workflow here
                // For example, you might update a 'currentState' field in the workflow or other related entities
            } else {
                System.out.println("Invalid state transition.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to check if the transition between states is valid (e.g., using transitions table)
    private boolean isTransitionValid(State fromState, State toState) {
        // Check if the transition between the two states is valid
        return transitionRepository.existsByFromStateIdAndToStateId(fromState.getId(), toState.getId());
    }
}
