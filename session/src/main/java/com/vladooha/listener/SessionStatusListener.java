package com.vladooha.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionStatusListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().getServletContext().log("SESSION CREATED");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        se.getSession().getServletContext().log("SESSION CLOSED");
    }
}
