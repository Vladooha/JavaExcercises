package com.vladooha.data.dao;

import com.vladooha.data.entity.Profile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileDAO extends AbstractDAO {
    private static final Logger logger = LogManager.getLogger(ProfileDAO.class);

    private static ProfileDAO instance;

    public static ProfileDAO getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new ProfileDAO();
            if (!instance.tryConnect()) {
                instance = null;
            }

            return instance;
        }
    }

    private ProfileDAO() { }

    public boolean addProfile(Profile profile) {
        String sql = String.format(
                "INSERT INTO person(name, surname) VALUES('%s', '%s')",
                profile.getName(),
                profile.getSurname());

        return execute(sql);
    }

    public Profile getLastProfile() {
        String sql = "SELECT name, surname FROM person ORDER BY name DESC LIMIT 1";
        ResultSet resultSet = executeQuery(sql);
        if (resultSet != null) {
            try {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    if (name != null && surname != null) {
                        return new Profile(name, surname);
                    } else {
                        logger.error("Error occurred till process result set in:" + sql);

                        return null;
                    }
                } else {
                    return null;
                }
            } catch (SQLException e) {
                logger.error("Wrong result set occurred by:" + sql);
                e.printStackTrace();

                return null;
            }
        } else {
            return null;
        }
    }

    public boolean removeProfile(Profile profile) {
        String sql = String.format(
                "DELETE FROM person WHERE name = '%s' AND surname = '%s'",
                profile.getName(),
                profile.getSurname());

        return executeUpdate(sql);
    }
}
