package entity.request;

import lombok.Getter;
import lombok.Setter;
//{
//  "login":"nastya",
// "password":"123456"
//}
@Setter
@Getter
public class AddMoneyRequest {
    private String token;
    private Integer id;
    private Integer addMoney;
}
