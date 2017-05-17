package com.aurea.deadcode.services.git;

import com.aurea.deadcode.exceptions.GitDownloadException;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alex on 5/14/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitRepositoryServiceTest {
    private static final String TEST_GIT_URI = "https://github.com/Alex-Persa/test-unused-code.git";
    private static final String INVALID_GIT_URI = "https://this.uri.is.not.a.valid.git.repository.git";
    private static final String DEST_PATH = "./repos/test_download_repo";

    @Autowired
    private GitRepositoryService gitRepositoryService;

    @Rule
    private ExpectedException thrown = ExpectedException.none();

    @Test
    public void testDownloadGit() {
        Date date = Calendar.getInstance().getTime();
        gitRepositoryService.cloneGitRepository(TEST_GIT_URI, "master", DEST_PATH);

        File f = new File(DEST_PATH + "/src/main/java/aa/C1.java");
        Assert.assertTrue(f.exists());
        Assert.assertTrue(FileUtils.isFileNewer(f, date));
        Assert.assertEquals(633, FileUtils.sizeOf(f));
    }

    @Test
    public void testDownloadGit_invalidRepository() {
        thrown.expect(GitDownloadException.class);
        thrown.expectMessage("org.eclipse.jgit.api.errors.JGitInternalException: Exception caught during execution of fetch command");
        gitRepositoryService.cloneGitRepository(INVALID_GIT_URI, "master", DEST_PATH);
    }

    @After
    public void cleanUp() {
        File repoFile = new File(DEST_PATH);
        if (repoFile.exists() || repoFile.isDirectory()) {
            try {
                FileUtils.deleteDirectory(repoFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
