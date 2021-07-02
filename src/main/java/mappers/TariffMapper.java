package mappers;

import entity.Bonus;
import entity.Tariff;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TariffMapper {
    public static final TariffMapper INSTANCE = new TariffMapper();
    private TariffMapper() {}
    public Tariff resultSetToEntity(ResultSet resultSet) throws SQLException {
        Tariff tariff = new Tariff();
        tariff.setId(resultSet.getInt("id"));
        tariff.setTitle(resultSet.getString("title"));
        tariff.setPrice(resultSet.getInt("price"));
        return tariff;
    }
}
