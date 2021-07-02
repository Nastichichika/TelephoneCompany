package repo;

import db.ConnectionFactory;
import db.ConnectionPool;
import entity.Bonus;
import entity.Tariff;
import lombok.val;
import mappers.BonusMapper;
import mappers.TariffMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BonusRepo {
    public static final BonusRepo INSTANCE = new BonusRepo();

    public List<Bonus> findAllBonuses(Integer id_usr ) {
        List<Bonus> allBonus = new ArrayList<>(  );
        String command = "SELECT id_bonus_root, title, price\n" +
                "FROM bonus INNER JOIN bonus_abonent On bonus.id_bonus_root = bonus_abonent.id_bonus\n" +
                "WHERE bonus_abonent.id_abonent = ?;";
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        ConnectionFactory.beginTransaction(connection, Connection.TRANSACTION_READ_COMMITTED);
        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setInt(1, id_usr);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                allBonus.add( BonusMapper.INSTANCE.resultSetToEntity(resultSet));
            }
            ConnectionFactory.commitTransaction(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            ConnectionFactory.rollbackTransaction(connection);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return allBonus;
    }
}
