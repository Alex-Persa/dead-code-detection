package com.aurea.deadcode.model.mappers;

import com.aurea.deadcode.model.dto.GitRepositoryDTO;
import com.aurea.deadcode.model.entities.GitRepository;
import org.modelmapper.ModelMapper;

/**
 * Created by Alex on 5/16/2017.
 */
public class GitRepositoryMapper {
    private ModelMapper mapper = new ModelMapper();

    public GitRepositoryDTO mapToDTO(GitRepository gitRepository) {
        return mapper.map(gitRepository, GitRepositoryDTO.class);
    }

}
