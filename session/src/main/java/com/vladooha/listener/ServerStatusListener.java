package com.vladooha.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServerStatusListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().log("SERVER STARTED");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().log("SERVER CLOSED");
    }
}
