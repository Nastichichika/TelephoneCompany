package service;

import entity.Bonus;
import entity.Tariff;
import entity.request.ChangeTariffRequest;
import entity.request.UserRequest;
import entity.response.BonusResponse;
import entity.response.TariffResponse;
import entity.response.UserResponse;
import repo.BonusRepo;
import repo.TariffRepo;
import repo.UserRepo;
import util.TokenUtil;

import java.util.List;

public class BonusService {
    public static BonusService INSTANCE = new BonusService();
    private BonusRepo bonusRepo = BonusRepo.INSTANCE;

    public BonusResponse getAllBonus(UserRequest request) {
        if(request.getId().equals(TokenUtil.getUserByToken(request.getToken()).getId())) {
            BonusResponse response = new BonusResponse();
            List<Bonus> bonusResponses = bonusRepo.findAllBonuses(request.getId()o);
            response.setBonus( bonusResponses );
            return response;
        }
        else {
            throw new RuntimeException("You cant see this");
        }
    }
    public void changeBonus(ChangeTariffRequest request) {
        TariffRepo.INSTANCE.changeTariff(request.getTariff_id(), request.getAbonent_id());
    }
}
