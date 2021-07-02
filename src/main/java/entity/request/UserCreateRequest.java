package entity.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {
    private String login;
    private String password;
    private String name;
    private String surname;
}