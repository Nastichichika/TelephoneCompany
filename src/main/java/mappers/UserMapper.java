package mappers;

import entity.User;
import entity.response.UserResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static UserMapper INSTANCE = new UserMapper();

    private UserMapper() {}

    public User resultSetToEntity(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setStatus(resultSet.getInt("status"));
        user.setRole(resultSet.getString("role"));
        return user;
    }

    public UserResponse toUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setLogin(user.getLogin());
        return response;
    }
}
