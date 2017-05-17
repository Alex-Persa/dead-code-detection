package com.aurea.deadcode.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Alex on 5/15/2017.
 */
@Getter
@Setter
@Entity
public class DeadCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String longName;
    private String simpleName;
    private String parameters;
    private String type;
    private String kind;
    private String fileName;
    private int line;
    private int column;

    @ManyToOne
    private GitRepository gitRepository;
}
