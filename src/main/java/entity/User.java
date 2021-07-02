package entity;

import lombok.Data;

@Data
public class User {

    private Integer id;

    private String Login;
    private String password;

    private String role;

    private String name;
    private String surname;
    private String phone_number;
    private Integer status;
}