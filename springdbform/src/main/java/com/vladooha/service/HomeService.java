package com.vladooha.service;

import com.vladooha.data.entity.Home;
import com.vladooha.data.dto.HomeDTO;
import com.vladooha.service.repo.QueryWrapper;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@Service
public class HomeService {
    private SessionFactory sessionFactory;
    private QueryWrapper queryWrapper;

    @Autowired
    public HomeService(EntityManagerFactory factory, QueryWrapper queryWrapper) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }

        this.sessionFactory = factory.unwrap(SessionFactory.class);
        this.queryWrapper = queryWrapper;
    }

    public List<HomeDTO> findAllDTO() {
        List<Home> homeList = findAll();

        List<HomeDTO> result = new ArrayList<>();
        for (Home home : homeList) {
            HomeDTO homeDTO = new HomeDTO(home);
            result.add(homeDTO);
        }

        return result;
    }

    public List<Home> findAll() {
        List<Home> result = queryWrapper.singleTransactionQuery(
                List.class,
                sessionFactory,
                (session) -> session.createQuery("FROM Home").list());

        return result;
    }

    public Home findById(long id) {
        Home result = queryWrapper.singleTransactionQuery(
                Home.class,
                sessionFactory,
                (session) -> session.get(Home.class, id));

        return result;
    }

    public long insertDTO(HomeDTO homeDTO) {
        Long resultCode = queryWrapper.singleTransactionQuery(
                Long.class,
                sessionFactory,
                (session) -> {
                    Home home = new Home();
                    home.mergeDTOwithouthId(homeDTO);

                    return session.save(home);
                });

        return resultCode != null ? resultCode : -1;
    }
}
