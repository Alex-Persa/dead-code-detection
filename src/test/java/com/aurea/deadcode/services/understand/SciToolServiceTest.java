package com.aurea.deadcode.services.understand;

import org.apache.commons.io.FileUtils;
import org.buildobjects.process.ExternalProcessFailureException;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Alex on 5/11/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SciToolServiceTest {

    private static final String REPO_PATH = "./test-repo";
    private static final String DB_FILE_NAME = "testDb.udb";
    private static final String DB_FILE_NAME2 = "testDb2.udb";
    private static final String JAVA_LANGUAGE = "Java";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private SciToolService sciToolService;

    @BeforeClass
    public static void setUp() throws IOException {
        Files.createDirectory(Paths.get(REPO_PATH));
        Files.createDirectory(Paths.get(REPO_PATH, "/sub-dir"));
        Files.createDirectory(Paths.get(REPO_PATH, "/sub-dir/package"));

        Files.createFile(Paths.get(REPO_PATH, "/1.java"));
        Files.createFile(Paths.get(REPO_PATH, "/sub-dir/package/2.java"));
        Files.createFile(Paths.get(REPO_PATH, "/3.java"));
        Files.createFile(Paths.get(REPO_PATH, "/4.non-java"));
        Files.createFile(Paths.get(REPO_PATH, "/.ignore"));
        Files.createFile(Paths.get(REPO_PATH, "textFile.txt"));
        Files.createFile(Paths.get(REPO_PATH, "fileWithNoExtension"));
        Files.createFile(Paths.get(REPO_PATH, "binaryFile.class"));
    }

    @Test
    public void testCreateUndDb() throws IOException, InterruptedException {
        sciToolService.createUndDb(REPO_PATH, DB_FILE_NAME, JAVA_LANGUAGE);

        File udbFile = new File(DB_FILE_NAME);
        Assert.assertTrue(udbFile.exists());
        Assert.assertTrue(udbFile.isFile());
        Assert.assertTrue(FileUtils.sizeOf(udbFile) > 9000);
    }

    @Test
    public void testCreateUndDb_twice() throws IOException {
        sciToolService.createUndDb(REPO_PATH, DB_FILE_NAME, JAVA_LANGUAGE);
        sciToolService.createUndDb(REPO_PATH, DB_FILE_NAME2, JAVA_LANGUAGE);

        File udbFile = new File(DB_FILE_NAME);
        Assert.assertTrue(udbFile.exists());
        udbFile = new File(DB_FILE_NAME2);
        Assert.assertTrue(udbFile.exists());
        FileUtils.forceDelete(udbFile);
    }

    @Test
    public void testCreateUndDb_invalidDestination() {
        thrown.expect(ExternalProcessFailureException.class);
        thrown.expectMessage("path \"this path is invalid * . 4141 /\\%$^\" contained a wildcard that did not result in any files.");
        sciToolService.createUndDb(REPO_PATH, "this path is invalid * . 4141 /\\%$^", JAVA_LANGUAGE);
    }

    @AfterClass
    public static void cleanUp() throws IOException {
        File f = new File(REPO_PATH);
        if (f.exists() && f.isDirectory()) {
            FileUtils.deleteDirectory(f);
        }

        f = new File(DB_FILE_NAME);
        if (f.exists()) {
            FileUtils.forceDelete(f);
        }
    }
}
