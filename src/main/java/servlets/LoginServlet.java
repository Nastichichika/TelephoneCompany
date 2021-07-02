package servlets;

import entity.request.LoginRequest;
import entity.response.TokenResponse;
import service.AuthorizationService;
import util.RequestUtil;
import util.ResponseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/login"})
public class LoginServlet extends HttpServlet {
    private final AuthorizationService authorizationService = AuthorizationService.INSTANCE;

    @Override//вход пользователя
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LoginRequest request = RequestUtil.getRequestObject(req, LoginRequest.class);

        log(request.getLogin());
        TokenResponse response =
                authorizationService.authorize(request.getLogin(), request.getPassword());

        ResponseUtil.sendResponse(resp, response);
    }
}
