package com.aurea.deadcode.model.dto;

/**
 * Created by Alex on 5/16/2017.
 */
public class AddRepositoryDTO {
    private String gitRepoURL;
    private String gitBranch;

    public String getGitRepoURL() {
        return gitRepoURL;
    }

    public void setGitRepoURL(String gitRepoURL) {
        this.gitRepoURL = gitRepoURL;
    }

    public String getGitBranch() {
        return gitBranch;
    }

    public void setGitBranch(String gitBranch) {
        this.gitBranch = gitBranch;
    }
}
