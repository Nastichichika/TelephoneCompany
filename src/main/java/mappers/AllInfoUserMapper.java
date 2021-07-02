package mappers;

import entity.User;
import entity.response.AllInfoUserResponse;
import entity.response.UserResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AllInfoUserMapper {
    public static AllInfoUserMapper INSTANCE = new AllInfoUserMapper();

    private AllInfoUserMapper() {}

    public AllInfoUserResponse resultSetToEntity(ResultSet resultSet) throws SQLException {
        AllInfoUserResponse user = new AllInfoUserResponse();
        user.setId(resultSet.getInt("usr_id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setStatus(resultSet.getInt("price"));
        user.setStatus(resultSet.getInt("status"));
        user.setMoney(resultSet.getInt("money"));
        user.setTariff(resultSet.getString("title_tariff"));
        user.setId_tariff(resultSet.getInt("id_tariff"));
        user.setDate(resultSet.getDate("last_change"));
        return user;
    }
}
