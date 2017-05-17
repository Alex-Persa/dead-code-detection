package com.aurea.deadcode.util;

import org.buildobjects.process.ProcBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Alex on 5/13/2017.
 */
public class ProcessExecutorUtils {

    private final static Logger logger = LoggerFactory.getLogger(ProcessExecutorUtils.class);

    private ProcessExecutorUtils() {
    }

    /**
     * Executes a given command.
     *
     * @param command the command to execute
     * @param args    command's args.
     */
    public static void execute(String command, String... args) {
        String output = new ProcBuilder(command)
                .withArgs(args)
                .withTimeoutMillis(100000)
                .withExpectedExitStatuses(0)
                .run()
                .getOutputString();

        logger.debug("process finished with output: " + output);
    }
}
