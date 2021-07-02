package servlets;


import entity.request.AddMoneyRequest;
import entity.request.UserRequest;
import entity.response.AllInfoUserResponse;
import service.UserService;
import util.RequestUtil;
import util.ResponseUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/abonent"})
public class AbonentServlet extends HttpServlet {
    private final UserService userService = UserService.INSTANCE;

    @Override//получить всю информацию о юзере
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        UserRequest request = RequestUtil.getRequestObject(req, UserRequest.class);
        AllInfoUserResponse response = userService.getAllInfoById(request);
        ResponseUtil.sendResponse(resp, response);
    }

    @Override//добавить денег юзеру или убрать если меняется тариф или бонусы
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        AddMoneyRequest request = RequestUtil.getRequestObject(req, AddMoneyRequest.class);
        userService.changeUserMoney(request);
    }
}

