package com.org.kafka.model;

import lombok.Data;

@Data
public class TransitionMessage {
    private Long claimId;
    private Long fromStateId;
    private Long toStateId;
}
