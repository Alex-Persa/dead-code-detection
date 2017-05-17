package com.aurea.deadcode.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Alex on 5/16/2017.
 */
@Getter
@Setter
public class AddRepositoryDTO {
    private String gitRepoURL;
    private String gitBranch;
}
