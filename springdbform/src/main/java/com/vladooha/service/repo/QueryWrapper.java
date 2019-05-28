package com.vladooha.service.repo;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

@Service
public class QueryWrapper {
    public <T> T singleTransactionQuery(Class<T> returnType, SessionFactory sessionFactory, QueryContainer queryContainer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        T result = null;
        try{
            transaction = session.beginTransaction();

            result = (T)queryContainer.doQuery(session);

            transaction.commit();
        } catch(HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return result;
    }
}
