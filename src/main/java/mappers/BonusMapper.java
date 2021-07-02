package mappers;

import entity.Bonus;
import entity.Tariff;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BonusMapper {
    public static final BonusMapper INSTANCE = new BonusMapper();
    private BonusMapper() {}
    public Bonus resultSetToEntity(ResultSet resultSet) throws SQLException {
        Bonus bonus = new Bonus();
        bonus.setId(resultSet.getInt("id"));
        bonus.setTitle(resultSet.getString("title"));
        bonus.setPrice(resultSet.getInt("price"));
        return bonus;
    }
}
