package servlets;

import entity.request.ChangeTariffRequest;
import entity.response.TariffResponse;
import service.TariffService;
import util.RequestUtil;
import util.ResponseUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet({"/tariffs"})
public class TariffServlet extends HttpServlet {
    private final TariffService tariffService = TariffService.INSTANCE;
    @Override//список всех тарифов
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TariffResponse response = tariffService.getAllTariff();
        ResponseUtil.sendResponse(resp, response);
    }
    @Override//изменить тариф пользователя
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ChangeTariffRequest request = RequestUtil.getRequestObject(req, ChangeTariffRequest.class);
        tariffService.changeTariff(request);
    }
}
