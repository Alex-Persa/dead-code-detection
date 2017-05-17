package com.aurea.deadcode.services.git;

import com.aurea.deadcode.exceptions.GitDownloadException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by Alex on 5/14/2017.
 */
@Service
public class GitRepositoryService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Clones a public github repository to a specific path.
     *
     * @param gitUri the uri to clone from
     * @param gitBranch the initial branch to check out when cloning the repository.
     *            Can be specified as ref name (<code>refs/heads/master</code>),
     *            branch name (<code>master</code>) or tag name (<code>v1.2.3</code>)
     * @param destPath The directory to clone to.
     */
    public void cloneGitRepository(String gitUri, String gitBranch, String destPath) {
        File destDirectory = new File(destPath);

        try {
            Git git = Git.cloneRepository()
                    .setURI(gitUri)
                    .setBranch(gitBranch)
                    .setDirectory(destDirectory)
                    .call();
            git.close();
            logger.debug("Repository " + gitUri + " cloned successfully on branch " + gitBranch);
        } catch (GitAPIException | JGitInternalException e) {
            logger.error("download git repo failed", e);
            throw new GitDownloadException(e);
        }
    }
}
