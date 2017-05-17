package com.aurea.deadcode.services.understand.deadcode;

import com.aurea.deadcode.model.entities.DeadCode;
import com.scitools.understand.Database;
import com.scitools.understand.Understand;
import com.scitools.understand.UnderstandException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.util.List;

/**
 * Created by Alex on 5/15/2017.
 */
public class DeadFunctionsServiceTest {
    private static Database db;
    private DeadFunctionsAnalyzerService deadFunctionsService = new DeadFunctionsAnalyzerService();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws UnderstandException {
        ClassLoader classLoader = getClass().getClassLoader();
        String udbPath = new File(classLoader.getResource("test1.udb").getFile()).getPath();
        db = Understand.open(udbPath);
    }

    @Test
    public void testDeadParameter() {
        DeadParameterAnalyzerService deadParameterService = new DeadParameterAnalyzerService();

        List<DeadCode> deadParams = deadParameterService.getDeadCode(db);

        Assert.assertEquals(7 ,deadParams.size());
        Assert.assertEquals("somepackage.otherpackage.C2.usedMethodWithUnusedVar.unusedParamInt" ,deadParams.get(3).getLongName());
    }

    @Test
    public void testDeadVariables() {
        DeadVariableAnalyzerService deadVariableService = new DeadVariableAnalyzerService();

        List<DeadCode> deadVariables = deadVariableService.getDeadCode(db);

        Assert.assertEquals(10 ,deadVariables.size());
        Assert.assertEquals("Private Static Variable" ,deadVariables.get(5).getKind());

    }

    @Test
    public void testDeadFunction() {
        List<DeadCode> deadCodes = deadFunctionsService.getDeadCode(db);

        Assert.assertEquals(4, deadCodes.size());

        Assert.assertEquals(16, deadCodes.get(0).getLine());
        Assert.assertEquals(29, deadCodes.get(0).getColumn());
        Assert.assertEquals("C1.java", deadCodes.get(0).getFileName());
        Assert.assertEquals("somepackage.otherpackage.C1.uncalledPrivateStaticFinalMethod", deadCodes.get(0).getLongName());
        Assert.assertEquals("Private Static Method", deadCodes.get(0).getKind());
        Assert.assertEquals("", deadCodes.get(0).getParameters());

        Assert.assertEquals(15, deadCodes.get(1).getLine());
        Assert.assertEquals(17, deadCodes.get(1).getColumn());
        Assert.assertEquals("Class1.java", deadCodes.get(1).getFileName());
        Assert.assertEquals("somepackage.Class1.uncalledPrivateMethod", deadCodes.get(1).getLongName());
        Assert.assertEquals("Private Method", deadCodes.get(1).getKind());
        Assert.assertEquals("", deadCodes.get(1).getParameters());

        Assert.assertEquals(19, deadCodes.get(2).getLine());
        Assert.assertEquals(25, deadCodes.get(2).getColumn());
        Assert.assertEquals("Class1.java", deadCodes.get(2).getFileName());
        Assert.assertEquals("somepackage.Class1.unusedFinalMethod", deadCodes.get(2).getLongName());
        Assert.assertEquals("Private Method", deadCodes.get(2).getKind());
        Assert.assertEquals("String,int,String", deadCodes.get(2).getParameters());

        Assert.assertEquals(11, deadCodes.get(3).getLine());
        Assert.assertEquals(23, deadCodes.get(3).getColumn());
        Assert.assertEquals("Class1.java", deadCodes.get(3).getFileName());
        Assert.assertEquals("somepackage.Class1.unusedPrivateStaticMethod", deadCodes.get(3).getLongName());
        Assert.assertEquals("Private Static Method", deadCodes.get(3).getKind());
        Assert.assertEquals("", deadCodes.get(3).getParameters());
    }


    @Test
    public void testDeadFunction_nullDb() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("db must not be null");

        deadFunctionsService.getDeadCode(null);
    }
}
