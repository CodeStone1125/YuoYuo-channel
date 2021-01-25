package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.json.JSONObject;

import app.*;
import tools.JsonReader;

public class LoginController extends HttpServlet {
    /** 版本號，避免版本不相容而導致error */
    private static final long serialVersionUID = 1L;
    private MemberHelper mh = MemberHelper.getHelper();
    private OrderHelper oh = OrderHelper.getHelper();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /** 解析傳入的request */
        JsonReader jsr = new JsonReader(request);
        /** 將request中的資料轉成json */
        JSONObject jso = jsr.getObject();
        /** 回傳的json */
        JSONObject resp = new JSONObject();

        /** 取得request中的參數 */
        String email = jso.getString("email");
        String password = jso.getString("password");

        Member member = new Member(email, password);
        int id = mh.checkLeveltime(member);
        if (id != 0) {
            Order order = new Order(id, 2);
            oh.updatestatus(order);
        }
        JSONObject m = mh.login(member);

        if (m != null) {
            request.getSession().setAttribute("id", m.getInt("id"));
            request.getSession().setAttribute("lastname", m.getString("lastname"));
            request.getSession().setAttribute("firstname", m.getString("firstname"));
            request.getSession().setAttribute("email", m.getString("email"));
            request.getSession().setAttribute("level", m.getInt("level"));
            request.getSession().setAttribute("phonenumber", m.getString("phonenumber"));

            resp.put("message", "登入成功！");
        } else {
            resp.put("errors", "帳號密碼錯誤！");
        }

        jsr.response(resp, response);
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /** 解析傳入的request */
        JsonReader jsr = new JsonReader(request);
        /** 回傳的json */
        JSONObject resp = new JSONObject();

        request.getSession().invalidate();

        resp.put("message", "登出成功！");

        jsr.response(resp, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        /** 回傳的json */
        JSONObject resp = new JSONObject();
        resp.put("message", "id獲取成功！");
        resp.put("id", mh.getLoginid(request));
        resp.put("level", mh.getlevel(request));
        jsr.response(resp, response);
    }
}