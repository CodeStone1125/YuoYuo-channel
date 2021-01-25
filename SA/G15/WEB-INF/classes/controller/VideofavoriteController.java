package controller;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;
import app.Videofavorite;
import app.VideofavoriteHelper;
import tools.JsonReader;

public class VideofavoriteController extends HttpServlet {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private VideofavoriteHelper vfh = VideofavoriteHelper.getHelper();

    /**
     * 處理Http Method請求GET方法（取得資料）
     *
     * @param request  Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException      Signals that an I/O exception has occurred.
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        JSONObject jso = jsr.getObject();
        int id = jso.getInt("id");

        /** 透過MemberHelper物件的getByID()方法自資料庫取回該名會員之資料，回傳之資料為JSONObject物件 */
        JSONObject query = vfh.getByID(id);

        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "會員資料取得成功");
        resp.put("response", query);

        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
JsonReader jsr = new JsonReader(request);
JSONObject jso = jsr.getObject();

/** 取出經解析到JSONObject之Request參數 */
int id = jso.getInt("id");

/** 透過MemberHelper物件的deleteByID()方法至資料庫刪除該名會員，回傳之資料為JSONObject物件 */
System.out.println("testcontroller");
JSONObject query = vfh.deleteByID(id);

/** 新建一個JSONObject用於將回傳之資料進行封裝 */
JSONObject resp = new JSONObject();
resp.put("status", "200");
resp.put("message", "會員移除成功！");
resp.put("response", query);

/** 透過JsonReader物件回傳到前端（以JSONObject方式） */
jsr.response(resp, response);
}




    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();

        /** 取出經解析到JSONObject之Request參數 */
        int memberid = jso.getInt("memberid");
        int videoid = jso.getInt("videoid");
        /** 建立一個新的會員物件 */
        Videofavorite vf = new Videofavorite(videoid, memberid);

        /** 透過MemberHelper物件的checkDuplicate()檢查該會員電子郵件信箱是否有重複 */
        if (!vfh.checkDuplicate(vf)) {
            /** 透過MemberHelper物件的create()方法新建一個會員至資料庫 */
            JSONObject data = vfh.create(vf);

            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功! 新增最愛...");
            resp.put("response", data);

            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        } else {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'新增最愛失敗，此影片已在最愛！\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
    }

}