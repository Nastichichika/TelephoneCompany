package entity.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeStatusRequest {
    private Integer status;
    private Integer abonent_id;
}
