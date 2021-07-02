package entity.response;

import entity.Bonus;
import entity.Tariff;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BonusResponse {
    private List<Bonus> bonus;
}
