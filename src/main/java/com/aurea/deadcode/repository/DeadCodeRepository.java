package com.aurea.deadcode.repository;

import com.aurea.deadcode.model.entities.DeadCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Alex on 5/16/2017.
 */
@Repository
public interface DeadCodeRepository extends CrudRepository<DeadCode, Long> {
}
