package com.org.fms.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.fms.model.Workflow;
import com.org.fms.repository.WorkflowRepository;

@Service
public class WorkflowService {

    @Autowired
    private WorkflowRepository workflowRepository;

    public Workflow getWorkflowByName(String name) {
        return workflowRepository.findByName(name);
    }
}
