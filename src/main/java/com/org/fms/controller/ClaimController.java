package com.org.fms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.fms.model.Claim;
import com.org.fms.service.ClaimService;

@RestController
@RequestMapping("/claims")
public class ClaimController {

	@Autowired
    private ClaimService claimService;

    // Create a new claim
    @PostMapping("/create")
    public Claim createClaim(@RequestParam String claimNumber, @RequestParam String workflowName) {
        return claimService.createClaim(claimNumber, workflowName);
    }

    // Transition the claim to the next state in the current stage
    @PostMapping("/{claimId}/transition")
    public Claim transitionToNextState(@PathVariable Long claimId) {
        return claimService.transitionToNextState(claimId);
    }

    // Transition the claim to a specific state
    @PostMapping("/{claimId}/transition-to-state/{stateId}")
    public Claim transitionToSpecificState(@PathVariable Long claimId, @PathVariable Long stateId) {
        return claimService.transitionToSpecificState(claimId, stateId);
    }

    // Transition the claim to the next stage in the workflow
    @PostMapping("/{claimId}/transition-to-next-stage")
    public Claim transitionToNextStage(@PathVariable Long claimId) {
        return claimService.transitionToNextStage(claimId);
    }

    // Transition the claim to the previous stage in the workflow
    @PostMapping("/{claimId}/transition-to-previous-stage")
    public Claim transitionToPreviousStage(@PathVariable Long claimId) {
        return claimService.transitionToPreviousStage(claimId);
    }

    // Reset the claim to its initial state and stage
    @PostMapping("/{claimId}/reset-to-initial-state")
    public Claim resetToInitialState(@PathVariable Long claimId) {
        return claimService.resetToInitialState(claimId);
    }

    // Additional endpoints for transitioning between states and stages can be added here}
}