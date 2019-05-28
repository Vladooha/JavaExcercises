package com.vladooha.service.repo;

import org.hibernate.Session;

public interface QueryContainer {
    Object doQuery(Session session);
}
