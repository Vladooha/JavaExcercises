package com.vladooha.service;

import com.vladooha.data.entity.Home;
import com.vladooha.data.entity.Profile;
import com.vladooha.dto.ProfileDTO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {
    private SessionFactory sessionFactory;

    @Autowired
    public ProfileService(EntityManagerFactory factory) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }

    public List<ProfileDTO> findAllDTO() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List profileList = new ArrayList();
        try{
            transaction = session.beginTransaction();

            profileList = (List)session.createQuery("FROM Profile").list();

            transaction.commit();
        } catch(HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        List<ProfileDTO> result = new ArrayList<>(profileList.size());
        for (Object obj : profileList) {
            if (obj instanceof Profile) {
                Profile profile = (Profile) obj;
                ProfileDTO profileDTO = new ProfileDTO(profile);
                result.add(profileDTO);
            }
        }

        return result;
    }

    public List<ProfileDTO> findAllSettledDTO() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List profileList = new ArrayList();
        try{
            transaction = session.beginTransaction();

            profileList = (List)session.createQuery("FROM Profile p WHERE p.home IS NOT NULL").list();

            transaction.commit();
        } catch(HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        List<ProfileDTO> result = new ArrayList<>(profileList.size());
        for (Object obj : profileList) {
            if (obj instanceof Profile) {
                Profile profile = (Profile) obj;
                ProfileDTO profileDTO = new ProfileDTO(profile);
                result.add(profileDTO);
            }
        }

        return result;
    }

    public Profile findById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Profile profile = null;
        try{
            transaction = session.beginTransaction();

            profile = session.get(Profile.class, id);

            transaction.commit();
        } catch(HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return profile;
    }

    public Integer addHome(long id, Home home) {
        System.err.println("ADD HOME: profile - " + id + ", home - " + home.getId());

        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer result = null;
        try{
            transaction = session.beginTransaction();

            Query query = session.createQuery("update Profile set home = :home where id = :id");
            query.setParameter("home", home);
            query.setParameter("id", id);
            result = query.executeUpdate();

            transaction.commit();
        } catch(HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return result;
    }

    public long insertDTO(ProfileDTO profileDTO) {
        Profile profile = new Profile();
        profile.mergeDTOwithouthId(profileDTO);

        Long savedId = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();

            savedId = (Long) session.save(profile);

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
}
