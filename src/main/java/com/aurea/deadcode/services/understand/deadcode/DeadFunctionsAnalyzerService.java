package com.aurea.deadcode.services.understand.deadcode;

import com.scitools.understand.Entity;
import org.springframework.stereotype.Service;

/**
 * Created by Alex on 5/15/2017.
 */
@Service
public class DeadFunctionsAnalyzerService extends DeadCodeAnalyzerService {

    @Override
    public String getEntityKindsQuery() {
        return "method private ~constructor ~unknown ~unused ~unresolved";
    }

    @Override
    public String getRefKindString() {
        return "Callby";
    }

    @Override
    protected boolean isFalsePositive(Entity entity) {
        return false;
    }


}
