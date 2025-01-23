-- Creating workflows for Claims and Orders
INSERT INTO workflows (name, type) VALUES 
('Claim Management', 'CLAIM'),
('Order Management', 'ORDER');


-- Stages for Claim Workflow
INSERT INTO stages (name, workflow_id) VALUES 
('Claim Initiation', 1),
('Claim Review', 1),
('Claim Approval', 1),
('Claim Settlement', 1);


-- Stages for Order Workflow
INSERT INTO stages (name, workflow_id) VALUES 
('Order Placement', 2),
('Payment Processing', 2),
('Order Fulfillment', 2),
('Shipping', 2),
('Delivery', 2);



-- States for Claim Workflow (Claim Management)
INSERT INTO states (name, stage_id) VALUES 
('Claim Submitted', 1),  -- Stage 1 (Claim Initiation)
('Claim In Progress', 2),  -- Stage 2 (Claim Review)
('Claim Approved', 3),  -- Stage 3 (Claim Approval)
('Claim Settled', 4);  -- Stage 4 (Claim Settlement)


-- States for Order Workflow (Order Management)
INSERT INTO states (name, stage_id) VALUES 
('Order Placed', 1),  -- Stage 1 (Order Placement)
('Payment Pending', 2),  -- Stage 2 (Payment Processing)
('Order Processed', 3),  -- Stage 3 (Order Fulfillment)
('Shipped', 4),  -- Stage 4 (Shipping)
('Delivered', 5);  -- Stage 5 (Delivery)


-- Transitions for Claim Workflow
INSERT INTO transitions (from_state_id, to_state_id, description) VALUES 
(1, 2, 'Claim is being reviewed'),  -- "Claim Submitted" → "Claim In Progress"
(2, 3, 'Claim is approved'),  -- "Claim In Progress" → "Claim Approved"
(3, 4, 'Claim has been settled');  -- "Claim Approved" → "Claim Settled"


-- Transitions for Order Workflow
INSERT INTO transitions (from_state_id, to_state_id, description) VALUES 
(1, 2, 'Payment is being processed'),  -- "Order Placed" → "Payment Pending"
(2, 3, 'Order is processed'),  -- "Payment Pending" → "Order Processed"
(3, 4, 'Order has been shipped'),  -- "Order Processed" → "Shipped"
(4, 5, 'Order has been delivered');  -- "Shipped" → "Delivered"


-- Example Claims
INSERT INTO claims (claim_number, workflow_id, current_stage_id, current_state_id) VALUES 
('CLAIM-123', 1, 1, 1),  -- Claim in "Claim Initiation" and state "Claim Submitted"
('CLAIM-124', 1, 2, 2);  -- Claim in "Claim Review" and state "Claim In Progress"


-- Example Orders
INSERT INTO orders (order_number, workflow_id, current_stage_id, current_state_id) VALUES 
('ORDER-123', 2, 1, 1),  -- Order in "Order Placement" and state "Order Placed"
('ORDER-124', 2, 3, 3);  -- Order in "Order Fulfillment" and state "Order Processed"


