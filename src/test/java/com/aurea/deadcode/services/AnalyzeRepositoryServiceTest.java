package com.aurea.deadcode.services;

import com.aurea.deadcode.model.ProcessingState;
import com.aurea.deadcode.model.entities.DeadCode;
import com.aurea.deadcode.model.entities.GitRepository;
import com.aurea.deadcode.repository.GitRepositoryRepository;
import com.aurea.deadcode.services.git.GitRepositoryService;
import com.aurea.deadcode.services.understand.SciToolService;
import com.aurea.deadcode.services.understand.UnderstandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;


/**
 * Created by Alex on 5/17/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnalyzeRepositoryServiceTest {

    private static final String GIT_URL = "some url";
    private static final String GIT_BRANCH = "master";
    private static final String JAVA_LANGUAGE = "Java";
    @MockBean
    private GitRepositoryRepository gitRepositoryRepository;

    @MockBean
    private GitRepositoryService gitRepositoryService;

    @MockBean
    private SciToolService sciToolService;

    @MockBean
    private UnderstandService understandService;

    @MockBean
    private DeadCodeService deadCodeService;

    @Autowired
    private AnalyzeRepositoryService toTest;

    @Test
    public void testAnalyzeRepo() throws InterruptedException {
        GitRepository gitRep = Mockito.mock(GitRepository.class);
        given(gitRep.getGitBranch()).willReturn(GIT_BRANCH);
        given(gitRep.getGitRepoURL()).willReturn(GIT_URL);

        List<DeadCode> deadCodeList = new ArrayList<>();
        given(understandService.getAllDeadCodeOccurrences(any(String.class))).willReturn(deadCodeList);

        toTest.analyzeRepo(gitRep);
        Thread.sleep(100);

        then(gitRepositoryService).should(times(1)).cloneGitRepository(eq(GIT_URL), eq(GIT_BRANCH), any(String.class));
        then(sciToolService).should(times(1)).createUndDb(any(String.class), any(String.class), eq(JAVA_LANGUAGE));
        then(understandService).should(times(1)).getAllDeadCodeOccurrences(any(String.class));
        then(deadCodeService).should(times(1)).saveDeadCode(deadCodeList, gitRep);
        then(gitRepositoryRepository).should(times(5)).save(gitRep); //add time stamps and update state.

        //test that any state was pass through
        then(gitRep).should(times(1)).setState(ProcessingState.DOWNLOADING);
        then(gitRep).should(times(1)).setState(ProcessingState.CREATING_UDB);
        then(gitRep).should(times(1)).setState(ProcessingState.PROCESSING_DEADCODE);
        then(gitRep).should(times(1)).setState(ProcessingState.COMPLETED);

        then(gitRep).should(times(1)).setProcessStartedDownloadingStartedTime(any(Date.class));
        then(gitRep).should(times(1)).setCreatingUdbFileStartedTime(any(Date.class));
        then(gitRep).should(times(1)).setProcessingDeadCodeStatedTime(any(Date.class));
        then(gitRep).should(times(1)).setCompletedTime(any(Date.class));
    }
}
