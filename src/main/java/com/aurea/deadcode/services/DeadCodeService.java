package com.aurea.deadcode.services;

import com.aurea.deadcode.exceptions.NotFoundException;
import com.aurea.deadcode.exceptions.RepoAnalizeFinisedWithFailureException;
import com.aurea.deadcode.model.ProcessingState;
import com.aurea.deadcode.model.dto.DeadCodeDTO;
import com.aurea.deadcode.model.entities.DeadCode;
import com.aurea.deadcode.model.entities.GitRepository;
import com.aurea.deadcode.model.mappers.DeadCodeMapper;
import com.aurea.deadcode.repository.DeadCodeRepository;
import com.aurea.deadcode.repository.GitRepositoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 5/16/2017.
 */
@Service
public class DeadCodeService {


    @Autowired
    private DeadCodeRepository deadCodeRepository;

    @Autowired
    private GitRepositoryRepository gitRepositoryRepository;

    /**
     * Saved dead code occurrences corresponding to the given repository
     * @param deadCodeList the dead code occurrences
     * @param gitRepository the git repository linked to the dead codes
     */
    protected void saveDeadCode(List<DeadCode> deadCodeList, GitRepository gitRepository) {
        for(DeadCode deadCode : deadCodeList) {
            deadCode.setGitRepository(gitRepository);
        }
        deadCodeRepository.save(deadCodeList);

        gitRepository.setDeadCodes(deadCodeList);
        gitRepositoryRepository.save(gitRepository);
    }

    /**
     * Get all dead codes analyzed from a repository
     * @param id the repository id
     * @return a list with dead code locations.
     */
    public List<DeadCodeDTO> getDeadCode(Long id) {
        GitRepository gitRepository = gitRepositoryRepository.findOne(id);
        if(gitRepository == null) {
            throw new NotFoundException("A repository with id " + id + " does not exist");
        }
        if(gitRepository.getState().equals(ProcessingState.FAILED)) {
            throw new RepoAnalizeFinisedWithFailureException(gitRepository.getErrors());
        }

        List<DeadCodeDTO> deadCodeDTOList = new ArrayList<>();
        DeadCodeMapper mapper = new DeadCodeMapper();
        gitRepository.getDeadCodes().forEach( entity -> deadCodeDTOList.add(mapper.mapToDTO(entity)));

        return deadCodeDTOList;
    }
}
