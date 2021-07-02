package servlets;

import entity.request.ChangeTariffRequest;
import entity.request.UserRequest;
import entity.response.BonusResponse;
import entity.response.TariffResponse;
import service.BonusService;
import service.TariffService;
import util.RequestUtil;
import util.ResponseUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet({"/bonus"})
public class BonusServlet extends HttpServlet {
    private final BonusService bonusService = BonusService.INSTANCE;
    @Override//список всех бонусов не верных и верных
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserRequest request = RequestUtil.getRequestObject(req, UserRequest.class);
        BonusResponse response = bonusService.getAllBonus(request);
        ResponseUtil.sendResponse(resp, response);
    }
    @Override//добавить удалить пользователя
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ChangeTariffRequest request = RequestUtil.getRequestObject(req, ChangeTariffRequest.class);
        bonusService.changeBonus(request);
    }
}
