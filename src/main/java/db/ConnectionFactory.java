package db;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {

    private static DataSource dataSource;

    public static Connection getConnection() {
        if(dataSource == null) {
            synchronized (ConnectionFactory.class) {
                PropertiesConfiguration config = new PropertiesConfiguration();
                try {
                    config.load("db/liquibase.properties");
                } catch (ConfigurationException e) {
                    e.printStackTrace();
                }

                BasicDataSource ds = new BasicDataSource();
                ds.setDriverClassName("org.postgresql.Driver");
                ds.setUrl( config.getString("url"));
                ds.setUsername(config.getString("username"));
                ds.setPassword(config.getString("password"));
                ds.setMinIdle(5);
                ds.setMaxIdle(10);
                ds.setMaxOpenPreparedStatements(100);
                dataSource = ds;
            }
        }
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void beginTransaction(Connection connection, int isolationLevel) {
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(isolationLevel);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void commitTransaction(Connection connection) {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void rollbackTransaction(Connection connection) {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
