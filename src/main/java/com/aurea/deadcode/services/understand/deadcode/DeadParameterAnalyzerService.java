package com.aurea.deadcode.services.understand.deadcode;

import com.scitools.understand.Entity;
import com.scitools.understand.Reference;
import org.springframework.stereotype.Service;

/**
 * Created by Alex on 5/15/2017.
 */
@Service
public class DeadParameterAnalyzerService extends DeadCodeAnalyzerService {

    @Override
    public String getEntityKindsQuery() {
        return "Java Parameter, Java Catch Parameter";
    }

    @Override
    public String getRefKindString() {
        return "Useby";
    }

    @Override
    protected boolean isFalsePositive(Entity entity) {
        Reference[] references = entity.refs("Definein", null, true);
        if(references != null && references.length > 0) {
            String kindName = references[0].ent().kind().name();
            if("Abstract Method".equalsIgnoreCase(kindName)) {
                return true;
            }
        }
        return false;
    }
}
