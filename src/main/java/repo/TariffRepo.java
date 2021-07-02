package repo;

import db.ConnectionFactory;
import db.ConnectionPool;
import entity.Tariff;
import mappers.TariffMapper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TariffRepo {
    public static final TariffRepo INSTANCE = new TariffRepo();

    public List<Tariff> findAllTariffs( ) {
        List<Tariff> allTariffs = new ArrayList<>(  );
        String command = "SELECT * FROM tariff";
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        ConnectionFactory.beginTransaction(connection, Connection.TRANSACTION_READ_COMMITTED);
        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                allTariffs.add( TariffMapper.INSTANCE.resultSetToEntity(resultSet));
            }

            ConnectionFactory.commitTransaction(connection);
        } catch (SQLException e) {
            ConnectionFactory.rollbackTransaction(connection);
            e.printStackTrace();
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return allTariffs;
    }
    public Integer findCostById(Integer id) {
        String command = "SELECT price FROM tariff WHERE id=?";
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        ConnectionFactory.beginTransaction(connection, Connection.TRANSACTION_READ_COMMITTED);
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
    public void changeTariff(Integer tariff_id, Integer abonent_id) {
        System.out.println("ahvjbkn");
        String command = "UPDATE account SET tariff=? WHERE abonent=?";
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        ConnectionFactory.beginTransaction(connection, Connection.TRANSACTION_READ_COMMITTED);
        Integer price = findCostById(tariff_id)*-1;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {
            System.out.println(tariff_id + " " + abonent_id);
            preparedStatement.setInt(1, tariff_id);
            preparedStatement.setInt(2, abonent_id);

            preparedStatement.executeUpdate();
            UserRepo.INSTANCE.changeUserMoney(abonent_id, price);
            UserRepo.INSTANCE.changeUserLastChange(abonent_id);
            ConnectionFactory.commitTransaction(connection);
        } catch (SQLException e) {
            ConnectionFactory.rollbackTransaction(connection);
            e.printStackTrace();
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }
}
