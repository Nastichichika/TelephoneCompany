package service;

import db.ConnectionFactory;
import db.ConnectionPool;
import entity.User;
import entity.request.*;
import entity.response.AllInfoUserResponse;
import lombok.val;
import mappers.AllInfoUserMapper;
import repo.TariffRepo;
import repo.UserRepo;
import util.Encryptor;
import util.TokenUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService {
    public static UserService INSTANCE = new UserService();

    private UserRepo userRepo = UserRepo.INSTANCE;

    private UserService() {}

    public Optional<User> findByLogin(String username) {
        return userRepo.findByLogin(username);
    }

    public List<AllInfoUserResponse> getAllUsers(GetAllUsersRequest request, String token) {
        User user = TokenUtil.getUserByToken( token );
        if(!user.getRole( ).equals( "admin" )){
          throw new RuntimeException("not admin");
        }
        return userRepo.findAllUsers(request.getStatus());
    }

    public User getByLogin(String username) {
        return findByLogin(username)
                .orElseThrow(() ->
                                new RuntimeException(
                                        String.format("Can't find user by username=%s", username)));
    }
    public AllInfoUserResponse getAllInfoById(UserRequest request) {
        if(request.getId().equals(TokenUtil.getUserByToken(request.getToken()).getId())) {
            return userRepo.findAllInfoById(request.getId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    String.format("Can't find user by id=%s", request.getId())));
        }
        else {
            throw new RuntimeException("You cant take this information");
        }
    }
    public User create(UserCreateRequest request) {

        if (findByLogin(request.getLogin()).isPresent()) {
            throw new RuntimeException("User with username already exists");
        }

        User user = new User();
        user.setLogin(request.getLogin());
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setPassword(Encryptor.encode(request.getPassword()));
        user.setRole("abonent");
        user = userRepo.save(user);
        return user;
    }
    public void changeUserStatus(ChangeStatusRequest request, String token) {
        User user = TokenUtil.getUserByToken( token );
        if(!user.getRole( ).equals( "admin" )){
            throw new RuntimeException("not admin");
        }
        UserRepo.INSTANCE.changeUserStatus(request.getAbonent_id(), request.getStatus());
    }
    public void changeUserMoney(AddMoneyRequest request) {
        if(request.getId().equals(TokenUtil.getUserByToken(request.getToken()).getId())) {
            UserRepo.INSTANCE.changeUserMoney(request.getId(), request.getAddMoney());
        }
        else {
            throw new RuntimeException("You cant do this");
        }
    }
}
