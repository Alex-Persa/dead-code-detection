package com.aurea.deadcode.repository;

import com.aurea.deadcode.model.entities.GitRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Alex on 5/16/2017.
 */
@Repository
public interface GitRepositoryRepository extends CrudRepository<GitRepository, Long> {

    @Query("from GitRepository a where a.gitRepoURL=:gitUrl")
    GitRepository findByGitUrl(@Param("gitUrl") String gitUrl);
}
