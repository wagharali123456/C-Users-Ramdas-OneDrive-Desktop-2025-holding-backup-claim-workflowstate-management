package com.org.fms.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.fms.model.Order;
import com.org.fms.model.Stage;
import com.org.fms.model.State;
import com.org.fms.model.Workflow;
import com.org.fms.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WorkflowService workflowService;

    public Order createOrder(String orderNumber, String workflowName) {
        Workflow workflow = workflowService.getWorkflowByName(workflowName);
        Stage initialStage = workflow.getStages().get(0);  // First stage
        State initialState = initialStage.getStates().get(0);  // First state

        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setWorkflow(workflow);
        order.setCurrentStage(initialStage);
        order.setCurrentState(initialState);

        return orderRepository.save(order);
    }

    // Add additional methods to transition between states
}
