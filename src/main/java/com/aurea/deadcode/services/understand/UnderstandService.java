package com.aurea.deadcode.services.understand;

import com.aurea.deadcode.model.entities.DeadCode;
import com.aurea.deadcode.services.understand.deadcode.DeadCodeAnalyzerService;
import com.scitools.understand.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 5/13/2017.
 */
@Service
public class UnderstandService {
    private final static Logger logger = LoggerFactory.getLogger(UnderstandService.class);

    @Autowired
    private List<DeadCodeAnalyzerService> deadCodeAnalyzerServices;

    @Autowired
    private UnderstandDbServiceImpl understandDbService;

    /**
     * Returns all dead code occurrences from a given understand db using java understand plugin.
     * @param udbPath the path to the understand database file.
     * @return a list of dead code entities.
     */
    public List<DeadCode> getAllDeadCodeOccurrences(String udbPath) {
        logger.debug("opening: " + udbPath);
        Database db = understandDbService.getDb(udbPath);
        logger.debug("opened " + udbPath);

        List<DeadCode> deadCodes = new ArrayList<>();
        for(DeadCodeAnalyzerService deadCodeAnalyzerService : deadCodeAnalyzerServices) {
            logger.debug("analyzing: " + deadCodeAnalyzerService.getClass().getName());
            deadCodes.addAll(deadCodeAnalyzerService.getDeadCode(db));
            logger.debug("analyze finished");
        }

        return deadCodes;
    }
}
