package com.org.fms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.fms.model.Claim;
import com.org.fms.model.Stage;
import com.org.fms.model.State;
import com.org.fms.model.Workflow;
import com.org.fms.repository.ClaimRepository;

@Service
public class ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private WorkflowService workflowService;

    // Create a claim with the initial state and stage
    public Claim createClaim(String claimNumber, String workflowName) {
        Workflow workflow = workflowService.getWorkflowByName(workflowName);
        
        if (workflow == null || workflow.getStages().isEmpty()) {
            throw new IllegalStateException("No stages available in the workflow.");
        }

        Stage initialStage = workflow.getStages().get(0);
        if (initialStage.getStates().isEmpty()) {
            throw new IllegalStateException("No states available in the initial stage.");
        }

        State initialState = initialStage.getStates().get(0);

        Claim claim = new Claim();
        claim.setClaimNumber(claimNumber);
        claim.setWorkflow(workflow);
        claim.setCurrentStage(initialStage);
        claim.setCurrentState(initialState);

        return claimRepository.save(claim);
    }

    // Transition the claim to the next state in the same stage
    public Claim transitionToNextState(Long claimId) {
        Claim claim = claimRepository.findById(claimId)
            .orElseThrow(() -> new IllegalStateException("Claim not found"));

        Stage currentStage = claim.getCurrentStage();
        State currentState = claim.getCurrentState();

        // Find the index of the current state in the list of states
        int currentStateIndex = currentStage.getStates().indexOf(currentState);
        
        // Check if there's a next state in the same stage
        if (currentStateIndex >= 0 && currentStateIndex < currentStage.getStates().size() - 1) {
            State nextState = currentStage.getStates().get(currentStateIndex + 1);
            claim.setCurrentState(nextState);
            return claimRepository.save(claim);
        } else {
            throw new IllegalStateException("No next state available in the current stage.");
        }
    }

    // Transition the claim to a specific state
    public Claim transitionToSpecificState(Long claimId, Long stateId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new IllegalStateException("Claim not found"));

        // Find the state in the same stage
        State newState = claim.getCurrentStage().getStates().stream()
                .filter(state -> state.getId().equals(stateId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("State not found"));

        // Transition the claim to the new state
        claim.setCurrentState(newState);

        return claimRepository.save(claim);
    }

    // Transition the claim to the next stage in the workflow
    public Claim transitionToNextStage(Long claimId) {
        Claim claim = claimRepository.findById(claimId)
            .orElseThrow(() -> new IllegalStateException("Claim not found"));

        // Get the next stage in the workflow
        Stage currentStage = claim.getCurrentStage();
        Workflow workflow = claim.getWorkflow();
        int currentStageIndex = workflow.getStages().indexOf(currentStage);

        // Check if there's a next stage in the workflow
        if (currentStageIndex >= 0 && currentStageIndex < workflow.getStages().size() - 1) {
            Stage nextStage = workflow.getStages().get(currentStageIndex + 1);
            State initialState = nextStage.getStates().get(0);  // Start from the first state of the next stage
            claim.setCurrentStage(nextStage);
            claim.setCurrentState(initialState);  // Set to the first state in the next stage

            return claimRepository.save(claim);
        } else {
            throw new IllegalStateException("No next stage available in the workflow.");
        }
    }

    // Transition the claim to a previous stage (if needed)
    public Claim transitionToPreviousStage(Long claimId) {
        Claim claim = claimRepository.findById(claimId)
            .orElseThrow(() -> new IllegalStateException("Claim not found"));

        // Get the previous stage in the workflow
        Stage currentStage = claim.getCurrentStage();
        Workflow workflow = claim.getWorkflow();
        int currentStageIndex = workflow.getStages().indexOf(currentStage);

        // Check if there's a previous stage in the workflow
        if (currentStageIndex > 0) {
            Stage previousStage = workflow.getStages().get(currentStageIndex - 1);
            State initialState = previousStage.getStates().get(0);  // Start from the first state of the previous stage
            claim.setCurrentStage(previousStage);
            claim.setCurrentState(initialState);  // Set to the first state in the previous stage

            return claimRepository.save(claim);
        } else {
            throw new IllegalStateException("No previous stage available in the workflow.");
        }
    }

    // Reset the claim to its initial state and stage
    public Claim resetToInitialState(Long claimId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new IllegalStateException("Claim not found"));

        // Get the initial stage and state
        Workflow workflow = claim.getWorkflow();
        Stage initialStage = workflow.getStages().get(0);
        State initialState = initialStage.getStates().get(0);

        // Reset claim to initial stage and state
        claim.setCurrentStage(initialStage);
        claim.setCurrentState(initialState);

        return claimRepository.save(claim);
    }

    // Additional methods for transitioning between states and stages can be added here
}
