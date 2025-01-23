-- Table for Workflows
CREATE TABLE workflows (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type ENUM('CLAIM', 'ORDER') NOT NULL
);

-- Table for Stages
CREATE TABLE stages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    workflow_id BIGINT,
    FOREIGN KEY (workflow_id) REFERENCES workflows(id)
);

-- Table for States
CREATE TABLE states (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    stage_id BIGINT,
    FOREIGN KEY (stage_id) REFERENCES stages(id)
);

-- Table for Transitions
CREATE TABLE transitions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    from_state_id BIGINT,
    to_state_id BIGINT,
    description VARCHAR(255),
    FOREIGN KEY (from_state_id) REFERENCES states(id),
    FOREIGN KEY (to_state_id) REFERENCES states(id)
);

-- Table for Claims
CREATE TABLE claims (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    claim_number VARCHAR(255) NOT NULL,
    workflow_id BIGINT,
    current_stage_id BIGINT,
    current_state_id BIGINT,
    FOREIGN KEY (workflow_id) REFERENCES workflows(id),
    FOREIGN KEY (current_stage_id) REFERENCES stages(id),
    FOREIGN KEY (current_state_id) REFERENCES states(id)
);

-- Table for Orders
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(255) NOT NULL,
    workflow_id BIGINT,
    current_stage_id BIGINT,
    current_state_id BIGINT,
    FOREIGN KEY (workflow_id) REFERENCES workflows(id),
    FOREIGN KEY (current_stage_id) REFERENCES stages(id),
    FOREIGN KEY (current_state_id) REFERENCES states(id)
);

CREATE INDEX idx_workflow_id_claims ON claims(workflow_id);
CREATE INDEX idx_stage_id_claims ON claims(current_stage_id);
CREATE INDEX idx_state_id_claims ON claims(current_state_id);

CREATE INDEX idx_workflow_id_orders ON orders(workflow_id);
CREATE INDEX idx_stage_id_orders ON orders(current_stage_id);
CREATE INDEX idx_state_id_orders ON orders(current_state_id);

ALTER TABLE workflows ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE workflows ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE stages ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE stages ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE states ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE states ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE transitions ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE transitions ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE claims ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE claims ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE orders ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE orders ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;


ALTER TABLE claims ADD CONSTRAINT fk_claims_workflow FOREIGN KEY (workflow_id) REFERENCES workflows(id);
ALTER TABLE claims ADD CONSTRAINT fk_claims_stage FOREIGN KEY (current_stage_id) REFERENCES stages(id);
ALTER TABLE claims ADD CONSTRAINT fk_claims_state FOREIGN KEY (current_state_id) REFERENCES states(id);

ALTER TABLE orders ADD CONSTRAINT fk_orders_workflow FOREIGN KEY (workflow_id) REFERENCES workflows(id);
ALTER TABLE orders ADD CONSTRAINT fk_orders_stage FOREIGN KEY (current_stage_id) REFERENCES stages(id);
ALTER TABLE orders ADD CONSTRAINT fk_orders_state FOREIGN KEY (current_state_id) REFERENCES states(id);

