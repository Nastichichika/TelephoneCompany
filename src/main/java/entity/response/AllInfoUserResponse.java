package entity.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AllInfoUserResponse {
    private Integer id;
    private String name;
    private String surname;
    private Integer price;
    private String tariff;
    private Integer id_tariff;
    private Integer status;
    private Integer money;
    private Date date;
}
