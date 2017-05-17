package com.aurea.deadcode.services;

import com.aurea.deadcode.exceptions.AlreadyExistsException;
import com.aurea.deadcode.model.ProcessingState;
import com.aurea.deadcode.model.dto.AddRepositoryDTO;
import com.aurea.deadcode.model.dto.GitRepositoryDTO;
import com.aurea.deadcode.model.entities.GitRepository;
import com.aurea.deadcode.repository.GitRepositoryRepository;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;


/**
 * Created by Alex on 5/17/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepositoryServiceTest {

    private static final String GIT_URL = "git url";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @MockBean
    private GitRepositoryRepository gitRepositoryRepository;

    @MockBean
    private AnalyzeRepositoryService analyzeRepositoryService;

    @Autowired
    RepositoryService repositoryService;

    @Test
    public void testAdd() {
        given(gitRepositoryRepository.findByGitUrl(GIT_URL)).willReturn(null);

        GitRepository savedRepo = new GitRepository();
        savedRepo.setId(1L);
        given(gitRepositoryRepository.save(any(GitRepository.class))).willReturn(savedRepo);

        AddRepositoryDTO addRepoDTO = new AddRepositoryDTO();
        addRepoDTO.setGitRepoURL(GIT_URL);
        GitRepositoryDTO result = repositoryService.add(addRepoDTO);

        Assert.assertEquals(new Long(1), result.getId());

        then(gitRepositoryRepository).should(times(1)).findByGitUrl(GIT_URL);
        then(gitRepositoryRepository).should(times(1)).save(Matchers.argThat(getGitRepositoryMatcher()));
        then(analyzeRepositoryService).should(times(1)).analyzeRepo(savedRepo);
    }


    @Test
    public void testAdd_alreadyExists() {
        AddRepositoryDTO addRepoDTO = new AddRepositoryDTO();
        addRepoDTO.setGitRepoURL(GIT_URL);
        GitRepository gitRepository = new GitRepository();

        given(gitRepositoryRepository.findByGitUrl(GIT_URL)).willReturn(gitRepository);

        thrown.expect(AlreadyExistsException.class);

        repositoryService.add(addRepoDTO);
    }

    @Test
    public void testGetAll() {
        List<GitRepository> gitRepos = new LinkedList<>();
        GitRepository repo1 = new GitRepository();
        repo1.setId(2L);
        repo1.setGitRepoURL("url");
        repo1.setState(ProcessingState.PROCESSING_DEADCODE);
        gitRepos.add(repo1);
        given(gitRepositoryRepository.findAll()).willReturn(gitRepos);

        List<GitRepositoryDTO> result = repositoryService.getAll();

        Assert.assertEquals(1, result.size());
        GitRepositoryDTO gitRepositoryDTO = result.get(0);
        Assert.assertEquals(gitRepositoryDTO.getId(), repo1.getId());
        Assert.assertEquals(gitRepositoryDTO.getGitRepoURL(), repo1.getGitRepoURL());

    }


    public Matcher<GitRepository> getGitRepositoryMatcher() {
        return new ArgumentMatcher<GitRepository>() {
            @Override
            public boolean matches(Object argument) {
                if (argument instanceof GitRepository) {
                    GitRepository gitRepository = (GitRepository) argument;
                    if (!gitRepository.getGitRepoURL().equalsIgnoreCase(GIT_URL)) {
                        return false;
                    }
                    if (gitRepository.getState() != ProcessingState.ADDED) {
                        return false;
                    }
                    if (gitRepository.getAddedTime() == null) {
                        return false;
                    }
                    return true;
                }
                return false;
            }
        };
    }
}
