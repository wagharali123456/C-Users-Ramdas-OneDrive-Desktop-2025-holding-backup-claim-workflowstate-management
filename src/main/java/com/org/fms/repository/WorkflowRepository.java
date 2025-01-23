package com.org.fms.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.org.fms.model.Workflow;

public interface WorkflowRepository extends JpaRepository<Workflow, Long> {
    Workflow findByName(String name);
}
