package com.vladooha.data.dao;

import com.vladooha.service.DBConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AbstractDAO {
    private static final Logger logger = LogManager.getLogger(AbstractDAO.class);

    private DBConnector dbConnector;

    public boolean tryConnect() {
        dbConnector = DBConnector.getInstance();

        return dbConnector != null;
    }

    protected boolean execute(String sql) {
        Statement statement = dbConnector.getStatement();
        if (statement != null) {
            try {
                statement.execute(sql);

                return true;
            } catch (SQLException e) {
                logger.error("Unable to execute query: " + sql);
                e.printStackTrace();

                return false;
            }
        } else {
            return false;
        }
    }

    protected boolean executeUpdate(String sql) {
        Statement statement = dbConnector.getStatement();
        if (statement != null) {
            try {
                statement.executeUpdate(sql);

                return true;
            } catch (SQLException e) {
                logger.error("Unable to execute query: " + sql);
                e.printStackTrace();

                return false;
            }
        } else {
            return false;
        }
    }

    protected ResultSet executeQuery(String sql) {
        Statement statement = dbConnector.getStatement();
        if (statement != null) {
            try {
                return statement.executeQuery(sql);
            } catch (SQLException e) {
                logger.error("Unable to execute query: " + sql);
                e.printStackTrace();

                return null;
            }
        } else {
            return null;
        }
    }
}
