package repo;

import db.ConnectionFactory;
import db.ConnectionPool;
import entity.User;
import entity.response.AllInfoUserResponse;
import mappers.AllInfoUserMapper;
import mappers.UserMapper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepo {

    public static UserRepo INSTANCE = new UserRepo();

    public List<AllInfoUserResponse> findAllUsers(Integer status) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        ConnectionFactory.beginTransaction(connection, Connection.TRANSACTION_READ_COMMITTED);

        List<AllInfoUserResponse> allUsers = new ArrayList<>( );
        String command = "SELECT usr.id As usr_id, name, surname, price, tariff.id AS id_tariff, " +
                "tariff.title As title_tariff, status, money \n" +
                "FROM usr INNER JOIN account ON usr.id = account.abonent \n" +
                "INNER JOIN tariff ON account.tariff = tariff.id " +
                "WHERE status=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setInt(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                allUsers.add( AllInfoUserMapper.INSTANCE.resultSetToEntity(resultSet));
            }
            ConnectionFactory.commitTransaction(connection);
        } catch (SQLException e) {
            ConnectionFactory.rollbackTransaction(connection);
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return allUsers;
    }
    public void changeUserStatus(Integer id, Integer status){
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        ConnectionFactory.beginTransaction(connection, Connection.TRANSACTION_READ_COMMITTED);

        String command = "UPDATE account SET status=? WHERE abonent=? ";
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, status);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
            ConnectionFactory.commitTransaction(connection);
        } catch (SQLException throwables) {

            ConnectionFactory.rollbackTransaction(connection);
            throwables.printStackTrace();
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }
    public void changeUserMoney(Integer id, Integer money){
        Integer newMoney = findCurrentMoney(id) + money;
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        ConnectionFactory.beginTransaction(connection, Connection.TRANSACTION_READ_COMMITTED);
System.out.println("cghvjbkvh");
        String command = "UPDATE account SET money=? WHERE abonent=? ";
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, newMoney);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
            changeUserLastChange(id);
            ConnectionFactory.commitTransaction(connection);

        } catch (SQLException throwables) {
            ConnectionFactory.rollbackTransaction(connection);
            throwables.printStackTrace();
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public Optional<User> findByLogin(String login) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        ConnectionFactory.beginTransaction(connection, Connection.TRANSACTION_READ_COMMITTED);

        String command = "SELECT * FROM usr WHERE login=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            ConnectionFactory.commitTransaction(connection);
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(UserMapper.INSTANCE.resultSetToEntity(resultSet));
        } catch (SQLException e) {
            ConnectionFactory.rollbackTransaction(connection);
            e.printStackTrace();
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return Optional.empty();
    }
    public Integer findCurrentMoney(Integer id) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        ConnectionFactory.beginTransaction(connection, Connection.TRANSACTION_READ_COMMITTED);

        String command = "SELECT money FROM account WHERE abonent=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ConnectionFactory.commitTransaction(connection);
            if (!resultSet.next()) {
                return 0;
            }

            return resultSet.getInt(1);
        } catch (SQLException e) {
            ConnectionFactory.rollbackTransaction(connection);
            e.printStackTrace();
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return 0;
    }
    public Optional<User> findById(Integer id) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        ConnectionFactory.beginTransaction(connection, Connection.TRANSACTION_READ_COMMITTED);

        String command = "SELECT * FROM usr WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ConnectionFactory.commitTransaction(connection);
            if (!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(UserMapper.INSTANCE.resultSetToEntity(resultSet));
        } catch (SQLException e) {
            ConnectionFactory.rollbackTransaction(connection);
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }
    public Optional<AllInfoUserResponse> findAllInfoById(Integer id) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        ConnectionFactory.beginTransaction(connection, Connection.TRANSACTION_READ_COMMITTED);

        String command = "SELECT usr.id, name, surname, price, tariff.id, " +
                "tariff.title, status, money \n" +
                "FROM usr INNER JOIN account ON usr.id = account.abonent \n" +
                "INNER JOIN tariff ON account.tariff = tariff.id " +
                "WHERE usr.id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            ConnectionFactory.commitTransaction(connection);
            if (!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(AllInfoUserMapper.INSTANCE.resultSetToEntity(resultSet));
        } catch (SQLException e) {
            ConnectionFactory.rollbackTransaction(connection);
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public void changeUserLastChange(Integer id){
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        ConnectionFactory.beginTransaction(connection, Connection.TRANSACTION_READ_COMMITTED);

        String command = "UPDATE account SET date=? WHERE abonent=? ";
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));

            preparedStatement.executeUpdate();
            ConnectionFactory.commitTransaction(connection);
        } catch (SQLException e) {

            ConnectionFactory.rollbackTransaction(connection);
            e.printStackTrace();
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }
    public User save(User user) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        ConnectionFactory.beginTransaction(connection, Connection.TRANSACTION_READ_COMMITTED);
        String command =
                "INSERT INTO usr (login, password, name, surname, role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.setString(5, "abonent");
            preparedStatement.execute();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }

            ConnectionFactory.commitTransaction(connection);
        } catch (SQLException throwables) {
            ConnectionFactory.rollbackTransaction(connection);
            throwables.printStackTrace();
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return user;
    }
}
