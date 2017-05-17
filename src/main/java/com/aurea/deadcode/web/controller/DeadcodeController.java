package com.aurea.deadcode.web.controller;

import com.aurea.deadcode.model.dto.DeadCodeDTO;
import com.aurea.deadcode.services.DeadCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Alex on 5/10/2017.
 */
@RestController
@RequestMapping(path = "/api/dead-code")
@Api
public class DeadcodeController {

    @Autowired
    private DeadCodeService deadCodeService;

    @ApiOperation(value = "Get dead code occurrences for a given repository")
    @GetMapping(value = "/{repositoryId}")
    public List<DeadCodeDTO> testCreateUdb(@PathVariable Long repositoryId) {
        return deadCodeService.getDeadCode(repositoryId);
    }

}
