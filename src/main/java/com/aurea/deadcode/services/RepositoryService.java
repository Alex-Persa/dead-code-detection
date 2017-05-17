package com.aurea.deadcode.services;

import com.aurea.deadcode.exceptions.AlreadyExistsException;
import com.aurea.deadcode.model.ProcessingState;
import com.aurea.deadcode.model.dto.AddRepositoryDTO;
import com.aurea.deadcode.model.dto.GitRepositoryDTO;
import com.aurea.deadcode.model.entities.GitRepository;
import com.aurea.deadcode.model.mappers.GitRepositoryMapper;
import com.aurea.deadcode.repository.GitRepositoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Alex on 5/16/2017.
 */
@Service
public class RepositoryService {

    @Autowired
    private GitRepositoryRepository gitRepositoryRepository;

    @Autowired
    private AnalyzeRepositoryService analyzeRepositoryService;

    /**
     * Saved a GitRepositoryObject into database.
     * Adds an analyzing task into queue for processing the dead code from given repository.
     *
     * @param addRepositoryDTO the DTO for a github repo containing repository url and branch.
     * @return the saved GitRepository entity's corresponding dto.
     */
    public GitRepositoryDTO add(AddRepositoryDTO addRepositoryDTO) {
        GitRepository gitRepository = gitRepositoryRepository.findByGitUrl(addRepositoryDTO.getGitRepoURL());
        if (gitRepository != null) {
            throw new AlreadyExistsException("Already created");
        }

        gitRepository = new GitRepository();
        gitRepository.setGitRepoURL(addRepositoryDTO.getGitRepoURL());
        gitRepository.setGitBranch(addRepositoryDTO.getGitBranch());
        gitRepository.setState(ProcessingState.ADDED);
        gitRepository.setAddedTime(Calendar.getInstance().getTime());
        gitRepository = gitRepositoryRepository.save(gitRepository);

        analyzeRepositoryService.analyzeRepo(gitRepository);

        return new GitRepositoryMapper().mapToDTO(gitRepository);
    }

    /**
     * Get all saved repositories from data source.
     *
     * @return list of repositories.
     */
    public List<GitRepositoryDTO> getAll() {
        List<GitRepositoryDTO> gitRepositoryDTOs = new ArrayList<>();
        GitRepositoryMapper gitRepositoryMapper = new GitRepositoryMapper();
        gitRepositoryRepository.findAll().forEach(
                (entity) -> gitRepositoryDTOs.add(gitRepositoryMapper.mapToDTO(entity))
        );

        return gitRepositoryDTOs;
    }
}
