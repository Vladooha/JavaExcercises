package com.vladooha.service;

import com.vladooha.data.entity.Home;
import com.vladooha.data.entity.Profile;
import com.vladooha.data.dto.ProfileDTO;
import com.vladooha.service.repo.QueryWrapper;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {
    private SessionFactory sessionFactory;
    private QueryWrapper queryWrapper;

    @Autowired
    public ProfileService(EntityManagerFactory factory, QueryWrapper queryWrapper) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }

        this.sessionFactory = factory.unwrap(SessionFactory.class);
        this.queryWrapper = queryWrapper;
    }

    public List<ProfileDTO> findAllDTO() {
        List<Profile> profileList = queryWrapper.singleTransactionQuery(
                List.class,
                sessionFactory,
                (session) -> session.createQuery("FROM Profile").list());

        List<ProfileDTO> result = new ArrayList<>();
        for (Profile profile : profileList) {
            ProfileDTO profileDTO = new ProfileDTO(profile);
            result.add(profileDTO);
        }

        return result;
    }

    public List<ProfileDTO> findAllSettledDTO() {
        List<Profile> profileList = queryWrapper.singleTransactionQuery(
                List.class,
                sessionFactory,
                (session) -> session.createQuery("FROM Profile p WHERE p.home IS NOT NULL").list());

        List<ProfileDTO> result = new ArrayList<>();
        for (Profile profile : profileList) {
            ProfileDTO profileDTO = new ProfileDTO(profile);
            result.add(profileDTO);
        }

        return result;
    }

    public Profile findById(long id) {
        Profile result = queryWrapper.singleTransactionQuery(
                Profile.class,
                sessionFactory,
                (session) -> session.get(Profile.class, id));

        return result;
    }

    public int addHome(long id, Home home) {
        Integer resultCode = queryWrapper.singleTransactionQuery(
                Integer.class,
                sessionFactory,
                (session) -> {
                    Query query = session.createQuery("update Profile set home = :home where id = :id");
                    query.setParameter("home", home);
                    query.setParameter("id", id);

                    return query.executeUpdate();
                });

        return resultCode != null ? resultCode : -1;
    }

    public long insertDTO(ProfileDTO profileDTO) {
        Long resultCode = queryWrapper.singleTransactionQuery(
                Long.class,
                sessionFactory,
                (session) -> {
                    Profile profile = new Profile();
                    profile.mergeDTOwithouthId(profileDTO);

                    return (Long) session.save(profile);
                });

        return resultCode != null ? resultCode : -1;
    }
}
