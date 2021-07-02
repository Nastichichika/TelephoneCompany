package entity.response;

import entity.Tariff;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TariffResponse {
    private List<Tariff> tariffs;
}
