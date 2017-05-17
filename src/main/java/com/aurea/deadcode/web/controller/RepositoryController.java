package com.aurea.deadcode.web.controller;

import com.aurea.deadcode.model.dto.AddRepositoryDTO;
import com.aurea.deadcode.model.dto.GitRepositoryDTO;
import com.aurea.deadcode.services.RepositoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Alex on 5/10/2017.
 */
@RestController
@RequestMapping(path = "/api/dead-code/repositories")
@Api
public class RepositoryController {

    @Autowired
    private RepositoryService repositoryService;

    @ApiOperation(value = "Add a github repository")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public GitRepositoryDTO addRepository(@RequestBody AddRepositoryDTO addRepositoryDTO) {
        return repositoryService.add(addRepositoryDTO);
    }

    @ApiOperation(value = "Get all repositories")
    @GetMapping
    public List<GitRepositoryDTO> getAll() {
        return repositoryService.getAll();
    }
}
