package com.aurea.deadcode.services.understand;

import com.aurea.deadcode.model.entities.DeadCode;
import com.scitools.understand.UnderstandException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

/**
 * Created by Alex on 5/13/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UnderstandServiceTest {

    @Autowired
    private UnderstandService understandService;

    @Test
    public void testGetAllDeadCode() throws UnderstandException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test1.udb").getFile());
        List<DeadCode> res = understandService.getAllDeadCodeOccurrences(file.getPath());

        Assert.assertEquals(21, res.size());
    }
}
