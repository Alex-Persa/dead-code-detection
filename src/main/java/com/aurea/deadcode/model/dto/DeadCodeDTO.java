package com.aurea.deadcode.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Alex on 5/15/2017.
 */
@Getter
@Setter
public class DeadCodeDTO {
    private String longName;
    private String simpleName;
    private String parameters;
    private String type;
    private String kind;
    private String fileName;
    private int line;
    private int column;
}
