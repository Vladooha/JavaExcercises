package com.vladooha.service;

import com.vladooha.data.dao.AbstractDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DBConnector {
    private static final Logger logger = LogManager.getLogger(DBConnector.class);

    private static DBConnector instance = null;
    private static Connection connection = null;

    public static DBConnector getInstance() {
        if (instance != null) {
            return instance;
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/test_db?useSSL=false&characterEncoding=UTF8&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                        "vladooha",
                        "080199");

                return new DBConnector();
            } catch (ClassNotFoundException e) {
                logger.error("Unable to load DB driver");
                e.printStackTrace();

                return null;
            } catch (SQLException e) {
                logger.error("Unable to set connection with DB");
                e.printStackTrace();

                return null;
            }
        }
    }

    private DBConnector() { }

    public Statement getStatement() {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            logger.error("Unable to create statement");
            e.printStackTrace();

            return null;
        }
    }

    public void closeDb() {
        if (connection != null) {
            try {
                instance = null;
                connection.close();
            } catch (SQLException e) { }
        }
    }
}