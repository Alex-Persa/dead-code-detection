package com.aurea.deadcode.services.understand.deadcode;

import com.scitools.understand.Entity;
import org.springframework.stereotype.Service;

/**
 * Created by Alex on 5/15/2017.
 */
@Service
public class DeadVariableAnalyzerService extends DeadCodeAnalyzerService {

    @Override
    public String getEntityKindsQuery() {
        return "Variable Local, Variable Private Member";
    }

    @Override
    public String getRefKindString() {
        return "Useby";
    }

    @Override
    protected boolean isFalsePositive(Entity entity) {
        return false;
    }
}
