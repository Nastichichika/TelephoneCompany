package entity.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeTariffRequest {
    private Integer tariff_id;
    private Integer abonent_id;
}