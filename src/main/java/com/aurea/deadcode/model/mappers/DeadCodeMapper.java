package com.aurea.deadcode.model.mappers;

import com.aurea.deadcode.model.dto.DeadCodeDTO;
import com.aurea.deadcode.model.entities.DeadCode;
import org.modelmapper.ModelMapper;

/**
 * Created by Alex on 5/16/2017.
 */
public class DeadCodeMapper {
    private ModelMapper mapper = new ModelMapper();

    public DeadCodeDTO mapToDTO(DeadCode deadCode) {
        return mapper.map(deadCode, DeadCodeDTO.class);
    }

}
