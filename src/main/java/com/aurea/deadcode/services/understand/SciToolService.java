package com.aurea.deadcode.services.understand;

import com.aurea.deadcode.util.ProcessExecutorUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Created by Alex on 5/11/2017.
 */
@Service
public class SciToolService {

    @Value("${und.command.file.path}")
    private String sciToolBinPath;

    private static final Logger logger = Logger.getLogger(SciToolService.class.getName());

    /**
     * Creates understand database file (.udb) by calling und command from installed SciTools.
     * Add files from the given repository to udb file.
     * Calls analyze command on udb file using sciTools executable.
     *
     * @param repositoryPath root directory for the container to be analyze
     * @param dbFileName full or relative path to the destination of udb file
     * @param language udb file language. Excepted value "Java"
     */
    public void createUndDb(String repositoryPath, String dbFileName, String language) {
        createUdbFile(dbFileName, language);
        addRepositoryToUdb(repositoryPath, dbFileName);
        analyze(dbFileName);
    }

    private void createUdbFile(String udbFilePath, String language) {
        ProcessExecutorUtils.execute(sciToolBinPath, "create", "-languages", language, udbFilePath);
    }

    private void addRepositoryToUdb(String rootDirectory, String udbFilePath) {
        ProcessExecutorUtils.execute(sciToolBinPath, "add", rootDirectory, udbFilePath);
    }

    private void analyze(String udbFilePath) {
        ProcessExecutorUtils.execute(sciToolBinPath, "analyze", "-all", udbFilePath);
    }

}
