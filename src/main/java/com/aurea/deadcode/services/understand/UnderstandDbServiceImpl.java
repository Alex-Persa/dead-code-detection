package com.aurea.deadcode.services.understand;

import com.aurea.deadcode.services.AnalyzeRepositoryService;
import com.scitools.understand.Database;
import com.scitools.understand.Understand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * Created by Alex on 5/17/2017.
 */
@Service
class UnderstandDbServiceImpl {
    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    private final static Logger log = LoggerFactory.getLogger(AnalyzeRepositoryService.class);

    synchronized Database getDb(String metadataPath) {
        Callable<Database> task = () -> {
            Database db = null;
            try {
                log.debug("Opening db [{}]", metadataPath);
                db = Understand.open(metadataPath);
            } catch (Exception e) {
                log.error("Error in opening db.", e);
            }
            return db;
        };

        Future<Database> future = executor.submit(task);

        Database db = null;
        try {
            db = future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("Error in opening db.", e);
        }

        if (db == null) {
            throw new RuntimeException("Error in opening metadata of " + metadataPath);
        }
        log.debug("Db is opened for [{}]", metadataPath);
        return db;
    }
}
