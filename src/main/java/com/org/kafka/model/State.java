package com.org.kafka.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
public class State {

    @Id
    private Long id;

    private String name;

    @ManyToOne
    private Stage stage;  // State is part of a stage
}
