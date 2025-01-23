package com.org.fms.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.fms.model.Stage;

public interface StageRepository extends JpaRepository<Stage, Long> {
    List<Stage> findByWorkflowId(Long workflowId);
}
