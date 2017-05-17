package com.aurea.deadcode.services;

import com.aurea.deadcode.model.ProcessingState;
import com.aurea.deadcode.model.entities.DeadCode;
import com.aurea.deadcode.model.entities.GitRepository;
import com.aurea.deadcode.repository.GitRepositoryRepository;
import com.aurea.deadcode.services.git.GitRepositoryService;
import com.aurea.deadcode.services.understand.SciToolService;
import com.aurea.deadcode.services.understand.UnderstandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alex on 5/16/2017.
 */
@Service
public class AnalyzeRepositoryService {

    private final static Logger logger = LoggerFactory.getLogger(AnalyzeRepositoryService.class);

    private static final String WORKING_DIR = "./repos/";
    private static final String UDB_EXTENSION = ".udb";
    private static final String LANGUAGE = "Java";

    @Autowired
    private GitRepositoryRepository gitRepositoryRepository;

    @Autowired
    private GitRepositoryService gitRepositoryService;

    @Autowired
    private SciToolService sciToolService;

    @Autowired
    private UnderstandService understandService;

    @Autowired
    private DeadCodeService deadCodeService;

    /**
     * Asynchronously downloads the git repo, creates udb file, analyze it and save into db the found dead codes
     * @param gitRepository the git repository entity object on which to perform the analyze.
     */
    @Async
    public void analyzeRepo(GitRepository gitRepository) {
        try {
            doAnalyze(gitRepository);
        } catch (Exception e) {
            setErrorState(e, gitRepository);
        }
    }

    private void doAnalyze(GitRepository gitRepository) {
        setRepositoryState(ProcessingState.DOWNLOADING, gitRepository);
        String repoName = UUID.randomUUID().toString();
        String repoPath = WORKING_DIR + repoName;
        gitRepository.setRepoPath(repoPath);
        gitRepositoryService.cloneGitRepository(gitRepository.getGitRepoURL(), gitRepository.getGitBranch(), repoPath);

        setRepositoryState(ProcessingState.CREATING_UDB, gitRepository);
        String dbFileName = repoPath + UDB_EXTENSION;
        gitRepository.setUdbFileLocation(dbFileName);
        sciToolService.createUndDb(repoPath, dbFileName, LANGUAGE);

        setRepositoryState(ProcessingState.PROCESSING_DEADCODE, gitRepository);
        List<DeadCode> deadCodeList;
        deadCodeList = understandService.getAllDeadCodeOccurrences(dbFileName);

        setRepositoryState(ProcessingState.SAVING_RESULTS, gitRepository);
        logger.info("saving into db");
        deadCodeService.saveDeadCode(deadCodeList, gitRepository);
        logger.info("saved");
        setRepositoryState(ProcessingState.COMPLETED, gitRepository);
    }

    /**
     * Update the repository states with FAILED status, timestamp and error message.
     * @param e the exception from where to extract the error message
     * @param repository the repository to which to set the failed state.
     */
    private void setErrorState(Exception e, GitRepository repository) {
        repository.setState(ProcessingState.FAILED);
        repository.setCompletedTime(Calendar.getInstance().getTime());
        String error = e.getMessage();
        if (StringUtils.isEmpty(error) && error.length() > 254) {
            error = error.substring(0, 254);
        }
        repository.setErrors(error);

        gitRepositoryRepository.save(repository);
    }

    /**
     * Updates repository state and add timestamps
     *
     * @param processingState the state in which the repository to be updated
     * @param gitRepository   the repository to update
     */
    private void setRepositoryState(ProcessingState processingState, GitRepository gitRepository) {
        gitRepository.setState(processingState);
        Date currentTime = Calendar.getInstance().getTime();
        switch (processingState) {
            case DOWNLOADING:
                gitRepository.setProcessStartedDownloadingStartedTime(currentTime);
                break;
            case CREATING_UDB:
                gitRepository.setCreatingUdbFileStartedTime(currentTime);
                break;
            case PROCESSING_DEADCODE:
                gitRepository.setProcessingDeadCodeStatedTime(currentTime);
                break;
            case COMPLETED:
                gitRepository.setCompletedTime(currentTime);
        }
        gitRepositoryRepository.save(gitRepository);
    }
}
