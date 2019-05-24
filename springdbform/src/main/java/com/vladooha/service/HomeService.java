package com.vladooha.service;

import com.vladooha.data.entity.Home;
import com.vladooha.dto.HomeDTO;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@Service
public class HomeService {
    private SessionFactory sessionFactory;

    @Autowired
    public HomeService(EntityManagerFactory factory) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }

    public List<HomeDTO> findAllDTO() {
//        Session session = sessionFactory.openSession();
//        String hqlStr = "SELECT H FROM Home H";
//        Query query = session.createQuery(hqlStr);
//        List allHomes = query.list();

        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List homeList = new ArrayList();
        try{
            transaction = session.beginTransaction();

            homeList = (List)session.createQuery("FROM Home").list();

            transaction.commit();
        } catch(HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        List<HomeDTO> result = new ArrayList<>(homeList.size());
        for (Object obj : homeList) {
            if (obj instanceof Home) {
                Home home = (Home) obj;
                HomeDTO homeDTO = new HomeDTO(home);
                result.add(homeDTO);
            }
        }

        return result;
    }

    public List<Home> findAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List homeList = new ArrayList();
        try{
            transaction = session.beginTransaction();

            homeList = (List)session.createQuery("FROM Home").list();

            transaction.commit();
        } catch(HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        List<Home> result = new ArrayList<>(homeList.size());
        for (Object obj : homeList) {
            if (obj instanceof Home) {
                Home home = (Home) obj;
                result.add(home);
            }
        }

        return result;
    }

    public Home findById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Home home = null;
        try{
            transaction = session.beginTransaction();

            home = session.get(Home.class, id);

            transaction.commit();
        } catch(HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return home;
    }

    public long insertDTO(HomeDTO homeDTO) {
        Home home = new Home();
        home.mergeDTOwithouthId(homeDTO);

        Long savedId = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();

            savedId = (Long) session.save(home);

            transaction.commit();
        } catch(HibernateException e) {
            if(transaction != null)
                transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return savedId;
    }

//    private <F, T> List<T> convertListType(Class<F> castFrom, Class<T> castTo, List list) {
//        List<T> result = new ArrayList<>(list.size());
//        for (Object obj : list) {
//            if (obj.getClass().isAssignableFrom(castFrom)) {
//                F classObj = (F) obj;
//                T resultObj = castHomeToDTO(home);
//                result.add(homeDTO);
//            }
//        }
//    }
}
