package com.org.kafka.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Transition {

    @Id
    private Long id;

    private Long fromStateId;
    private Long toStateId;
}
