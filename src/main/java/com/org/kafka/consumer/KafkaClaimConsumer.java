package com.org.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.fms.model.Claim;
import com.org.fms.model.State;
import com.org.fms.repository.ClaimRepository;
import com.org.fms.repository.StateRepository;
import com.org.fms.repository.TransitionRepository;
import com.org.fms.repository.WorkflowRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaClaimConsumer {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private TransitionRepository transitionRepository;

    // Kafka Listener to listen to claim transition messages
    @KafkaListener(topics = "claim-transition", groupId = "claim-consumer-group")
    public void listenToClaimTransition(String message) {
        System.out.println("Received Kafka Message: " + message);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TransitionMessage transitionMessage = objectMapper.readValue(message, TransitionMessage.class);

            // Fetch claim by ID
            Claim claim = claimRepository.findById(transitionMessage.getClaimId())
                    .orElseThrow(() -> new RuntimeException("Claim not found"));

            State fromState = stateRepository.findById(transitionMessage.getFromStateId())
                    .orElseThrow(() -> new RuntimeException("From State not found"));

            State toState = stateRepository.findById(transitionMessage.getToStateId())
                    .orElseThrow(() -> new RuntimeException("To State not found"));

            // Validate and perform state transition (using transitions table)
            if (isTransitionValid(fromState, toState)) {
                claim.setCurrentState(toState);
                claimRepository.save(claim);  // Save the updated claim with the new state

                System.out.println("Claim ID " + claim.getId() + " transitioned from " + fromState.getName() + " to " + toState.getName());
            } else {
                System.out.println("Invalid state transition for Claim ID " + claim.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to validate if the state transition is valid
    private boolean isTransitionValid(State fromState, State toState) {
        return transitionRepository.existsByFromStateIdAndToStateId(fromState.getId(), toState.getId());
    }
}
