package service;

import entity.Tariff;
import entity.request.ChangeTariffRequest;
import entity.response.TariffResponse;
import repo.TariffRepo;

import java.util.List;

public class TariffService {
    public static TariffService INSTANCE = new TariffService();
    private TariffRepo tariffRepo = TariffRepo.INSTANCE;

    public TariffResponse getAllTariff() {
        TariffResponse response = new TariffResponse();
        List<Tariff> tariffResponses = tariffRepo.findAllTariffs();
        response.setTariffs( tariffResponses );
        return response;
    }
    public void changeTariff(ChangeTariffRequest request) {
        TariffRepo.INSTANCE.changeTariff(request.getTariff_id(), request.getAbonent_id());
    }
}
