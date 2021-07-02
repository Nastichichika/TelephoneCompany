package servlets;

import entity.User;
import entity.request.ChangeStatusRequest;
import entity.request.ChangeTariffRequest;
import entity.request.GetAllUsersRequest;
import entity.response.AllInfoUserResponse;
import service.UserService;
import util.RequestUtil;
import util.ResponseUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({"/admin"})
public class AdminServlet extends HttpServlet {
    private final UserService userService = UserService.INSTANCE;

    @Override//список всех пользователей
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = req.getParameter("token");
        GetAllUsersRequest request = RequestUtil.getRequestObject(req, GetAllUsersRequest.class);
        List<AllInfoUserResponse> response = userService.getAllUsers(request, token);
        ResponseUtil.sendResponse(resp, response);
    }

    @Override//изменить статус пользователя
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = req.getParameter("token");
        ChangeStatusRequest request = RequestUtil.getRequestObject(req, ChangeStatusRequest.class);
        userService.changeUserStatus(request, token);
    }
}
