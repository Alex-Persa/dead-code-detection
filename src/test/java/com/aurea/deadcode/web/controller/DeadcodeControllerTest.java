package com.aurea.deadcode.web.controller;

import com.aurea.deadcode.exceptions.NotFoundException;
import com.aurea.deadcode.model.dto.DeadCodeDTO;
import com.aurea.deadcode.services.DeadCodeService;
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
public class DeadcodeControllerTest {

    private static final Long REPO_ID = 13L;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private DeadCodeService deadCodeService;

    @Test
    public void testGetDeadCodes() throws Exception {
        List<DeadCodeDTO> deadCodeList = new ArrayList<>();
        DeadCodeDTO dc1 = new DeadCodeDTO();
        dc1.setSimpleName("dc1");
        deadCodeList.add(dc1);
        given(deadCodeService.getDeadCode(REPO_ID)).willReturn(deadCodeList);

        Object result = this.restTemplate.getForEntity("/api/dead-code/{repositoryId}", Object.class, REPO_ID);

        ResponseEntity<List<DeadCodeDTO>> res = (ResponseEntity<List<DeadCodeDTO>>) result;
        List<DeadCodeDTO> deadCodes = res.getBody();

        Assert.assertEquals(200, res.getStatusCodeValue());
        Assert.assertEquals(1, deadCodeList.size());
        Assert.assertEquals("dc1", deadCodeList.get(0).getSimpleName());
    }

    @Test
    public void testGet_noDeadCodes() throws Exception {

        List<DeadCodeDTO> deadCodeList = new ArrayList<>();
        given(deadCodeService.getDeadCode(REPO_ID)).willReturn(deadCodeList);

        Object result = this.restTemplate.getForEntity("/api/dead-code/{repositoryId}", Object.class, REPO_ID);

        ResponseEntity<List<DeadCodeDTO>> res = (ResponseEntity<List<DeadCodeDTO>>) result;
        List<DeadCodeDTO> deadCodes = res.getBody();

        Assert.assertEquals(200, res.getStatusCodeValue());
        Assert.assertTrue(deadCodes.isEmpty());
    }

    @Test
    public void testGet_invalidId() throws Exception {
        given(deadCodeService.getDeadCode(REPO_ID)).willThrow(new NotFoundException("A repository with id " + REPO_ID + " does not exist"));

        Object result = this.restTemplate.getForEntity("/api/dead-code/{repositoryId}", Object.class, REPO_ID);

        ResponseEntity<LinkedHashMap> res = (ResponseEntity<LinkedHashMap>) result;
        LinkedHashMap body = res.getBody();

        Assert.assertEquals(404, res.getStatusCodeValue());
        Assert.assertEquals(404, body.get("status"));
        Assert.assertEquals("Not Found", body.get("error"));
        Assert.assertEquals("No such entity", body.get("message"));
        Assert.assertEquals("/api/dead-code/13", body.get("path"));
        Assert.assertEquals("com.aurea.deadcode.exceptions.NotFoundException", body.get("exception"));

    }
}
