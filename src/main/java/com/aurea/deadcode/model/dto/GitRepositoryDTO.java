package com.aurea.deadcode.model.dto;

import com.aurea.deadcode.model.ProcessingState;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Alex on 5/16/2017.
 */
@Getter
@Setter
public class GitRepositoryDTO {
    private Long id;
    private String gitRepoURL;
    private String gitBranch;
    private ProcessingState state;
    private Date addedTime;
    private Date processStartedDownloadingStartedTime;
    private Date creatingUdbFileStartedTime;
    private Date processingDeadCodeStatedTime;
    private Date completedTime;
    private String errors;
}
