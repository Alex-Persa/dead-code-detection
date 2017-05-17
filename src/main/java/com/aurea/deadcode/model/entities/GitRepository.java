package com.aurea.deadcode.model.entities;

import com.aurea.deadcode.model.ProcessingState;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 5/16/2017.
 */
@Getter
@Setter
@Entity
public class GitRepository {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany(mappedBy = "gitRepository", cascade = CascadeType.ALL)
    private List<DeadCode> deadCodes;

    private String repoPath;
    private String udbFileLocation;
}
