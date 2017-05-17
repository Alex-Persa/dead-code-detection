package com.aurea.deadcode.services.understand.deadcode;

import com.aurea.deadcode.model.entities.DeadCode;
import com.scitools.understand.Database;
import com.scitools.understand.Entity;
import com.scitools.understand.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 5/15/2017.
 */
@Service
public abstract class DeadCodeAnalyzerService {

    private final static Logger logger = LoggerFactory.getLogger(DeadCodeAnalyzerService.class);

    protected abstract String getEntityKindsQuery();
    protected abstract String getRefKindString();
    protected abstract boolean isFalsePositive(Entity entity);

    /**
     * Retrieves a list of dead code anti-patterns from a given Understand database file.
     * @param db the database from where to query dead code occurrences.
     * @return the list of dead code anti-patterns occurrences.
     */
    public List<DeadCode> getDeadCode(Database db) {
        List<DeadCode> deadCodes = new ArrayList<>();

        if (db == null) {
            throw new IllegalArgumentException("db must not be null");
        }

        Entity[] entities = db.ents(getEntityKindsQuery());

        for (Entity entity : entities) {
            Reference[] refs = entity.refs(getRefKindString(), null, true);
            if (refs.length == 0 && !isFalsePositive(entity)) {
                deadCodes.add(mapEntityToDeadMethod(entity));
            }
        }

        return deadCodes;
    }

    /**
     * Retrieve all the needed information form an understand entity Object for identifying the specific code.
     * @param e understand entity
     * @return DeadCode model containing the needed information for highlighting specific code.
     */
    private DeadCode mapEntityToDeadMethod(Entity e) {
        Reference reference = e.refs("Definein", null, true)[0];

        DeadCode deadCode = new DeadCode();
        deadCode.setLongName(e.longname(true));
        deadCode.setParameters(e.parameters());
        deadCode.setType(e.type());
        deadCode.setKind(e.kind().name());
        deadCode.setFileName(reference.file().name());
        deadCode.setLine(reference.line());
        deadCode.setColumn(reference.column());

        return deadCode;
    }
}
