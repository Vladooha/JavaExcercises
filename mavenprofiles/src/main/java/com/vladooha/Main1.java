package com.vladooha;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main1 {
    private static final Logger logger = LogManager.getLogger(Main1.class);

    public static void main(String[] args) {
        logger.error("Profile 1 started");
    }
}
