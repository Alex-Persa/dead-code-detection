package com.aurea.deadcode.web.controller;

import com.aurea.deadcode.model.dto.AddRepositoryDTO;
import com.aurea.deadcode.model.dto.GitRepositoryDTO;
import com.aurea.deadcode.services.RepositoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.mockito.BDDMockito.given;

/**
 * Created by Alex on 5/17/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepositoryControllerTest {

    private static final String REPO_BASE_URL = "/api/dead-code/repositories";

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private RepositoryService repositoryService;

    @Test
    public void testGetAll() throws Exception {
        List<GitRepositoryDTO> expectedResult = new ArrayList<>();
        expectedResult.add(getGitRepositoryDTO1());
        expectedResult.add(getGitRepositoryDTO1());
        given(repositoryService.getAll()).willReturn(expectedResult);

        ResponseEntity result = this.restTemplate.getForEntity(REPO_BASE_URL, Object.class);

        List<LinkedHashMap<String, String>> body = (List<LinkedHashMap<String, String>>) result.getBody();

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(2, body.size());
        Assert.assertEquals("repo1 branch", body.get(0).get("gitBranch"));
    }

    /**
     * Tests that if the service returns a GitRepositoryDTO the controller will return it forward with status 201 created
     */
    @Test
    public void testAddRepository() {
        GitRepositoryDTO expectedResult = getGitRepositoryDTO1();
        AddRepositoryDTO addRepositoryDTO = new AddRepositoryDTO();
        addRepositoryDTO.setGitBranch(expectedResult.getGitBranch());

        given(repositoryService.add(addRepositoryDTO)).willReturn(expectedResult);

        ResponseEntity<GitRepositoryDTO> result = this.restTemplate.postForEntity(REPO_BASE_URL, addRepositoryDTO, GitRepositoryDTO.class);

        Assert.assertEquals(201, result.getStatusCodeValue());
    }

    private GitRepositoryDTO getGitRepositoryDTO1() {
        GitRepositoryDTO gitRepositoryDTO = new GitRepositoryDTO();
        gitRepositoryDTO.setGitBranch("repo1 branch");

        return gitRepositoryDTO;
    }
}
