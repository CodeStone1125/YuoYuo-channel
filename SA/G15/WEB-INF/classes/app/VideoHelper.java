package app;

import java.sql.*;
import java.util.Date;
import org.json.*;
import util.DBMgr;

public class VideoHelper {
    private VideoHelper() {

    }

    private static VideoHelper vh;
    private Connection conn = null;
    private PreparedStatement pres = null;

    public static VideoHelper getHelper() {
        /** Singleton檢查是否已經有VideoHelper物件，若無則new一個，若有則直接回傳 */
        if (vh == null)
            vh = new VideoHelper();

        return vh;
    }

    public JSONObject getAll() {
        /** 新建一個 Video 物件之 v 變數，用於紀錄每一位查詢回之商品資料 */
        Video v = null;
        /** 用於儲存所有檢索回之商品，以JSONArray方式儲存 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `sa`.`video`";

            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while (rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;

                /** 將 ResultSet 之資料取出 */
                int id = rs.getInt("video_id");
                String name = rs.getString("video_name");
                String category = rs.getString("video_category");
                String director = rs.getString("director");
                String introduction = rs.getString("introduction");
                String classification = rs.getString("classification");
                int vip = rs.getInt("vip");
                String coverpath = rs.getString("cover_path");
                String videopath = rs.getString("video_path");
                Date update = rs.getDate("video_update");

                /** 將每一筆商品資料產生一名新video物件 */
                v = new Video(id, name, category, director, introduction, classification, vip, coverpath, videopath,
                        update);
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jsa.put(v.getData());
            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }

        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);

        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }

    public JSONObject getByIdList(String data) {
        /** 新建一個 Video 物件之 v 變數，用於紀錄每一位查詢回之商品資料 */
        Video v = null;
        /** 用於儲存所有檢索回之商品，以JSONArray方式儲存 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            String[] in_para = DBMgr.stringToArray(data, ",");
            /** SQL指令 */
            String sql = "SELECT * FROM `sa`.`video` WHERE `video`.`video_id`";
            for (int i = 0; i < in_para.length; i++) {
                sql += (i == 0) ? "in (?" : ", ?";
                sql += (i == in_para.length - 1) ? ")" : "";
            }

            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            for (int i = 0; i < in_para.length; i++) {
                pres.setString(i + 1, in_para[i]);
            }
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while (rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;

                /** 將 ResultSet 之資料取出 */
                int id = rs.getInt("video_id");
                String name = rs.getString("video_name");
                String category = rs.getString("video_category");
                String director = rs.getString("director");
                String introduction = rs.getString("introduction");
                String classification = rs.getString("classification");
                int vip = rs.getInt("vip");
                String coverpath = rs.getString("cover_path");
                String videopath = rs.getString("video_path");
                Date update = rs.getDate("video_update");

                /** 將每一筆商品資料產生一名新Product物件 */
                v = new Video(id, name, category, director, introduction, classification, vip, coverpath, videopath,
                        update);
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jsa.put(v.getData());
            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }

        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);

        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }

    // 搜尋影片(編輯用
    public Video getById(String videoid) {
        /** 新建一個 Video 物件之 v 變數，用於紀錄每一位查詢回之商品資料 */
        Video v = null;
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `sa`.`video` WHERE `video`.`video_id` = ? LIMIT 1";

            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setString(1, videoid);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while (rs.next()) {
                /** 將 ResultSet 之資料取出 */

                int video_id = rs.getInt("video_id");
                String name = rs.getString("video_name");
                String category = rs.getString("video_category");
                String director = rs.getString("director");
                String introduction = rs.getString("introduction");
                String classification = rs.getString("classification");
                int vip = rs.getInt("vip");
                String coverpath = rs.getString("cover_path");
                String videopath = rs.getString("video_path");
                Date update = rs.getDate("video_update");

                /** 將每一筆商品資料產生一名新Product物件 */
                v = new Video(video_id, name, category, director, introduction, classification, vip, coverpath,
                        videopath, update);

            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }

        return v;
    }

    public JSONObject getByTheId(String videoid) {
        /** 新建一個 Video 物件之 v 變數，用於紀錄每一位查詢回之商品資料 */
        Video v = null;
        /** 記錄實際執行之SQL指令 */
        JSONArray jsa = new JSONArray();
        String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        long start_time = System.nanoTime();
        int row = 0;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `sa`.`video` WHERE `video`.`video_id` = ? LIMIT 1";

            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setString(1, videoid);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while (rs.next()) {
                /** 將 ResultSet 之資料取出 */
                row += 1;
                int video_id = rs.getInt("video_id");
                String name = rs.getString("video_name");
                String category = rs.getString("video_category");
                String director = rs.getString("director");
                String introduction = rs.getString("introduction");
                String classification = rs.getString("classification");
                int vip = rs.getInt("vip");
                String coverpath = rs.getString("cover_path");
                String videopath = rs.getString("video_path");
                Date update = rs.getDate("video_update");

                /** 將每一筆商品資料產生一名新Product物件 */
                v = new Video(video_id, name, category, director, introduction, classification, vip, coverpath,
                        videopath, update);
                jsa.put(v.getData());
            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }

        long end_time = System.nanoTime();
        long duration = (end_time - start_time);

        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }

    // 新增影片
    public JSONObject create(Video v) {

        String exexcute_sql = "";
        long start_time = System.nanoTime();
        int row = 0;

        try {

            conn = DBMgr.getConnection();
            String sql = "INSERT INTO `sa`.`video`(`video_name`, `video_category`, `director`, `introduction`, `classification`, `vip`"
                    + ",`cover_path`, `video_path`, `video_update`)" + " VALUES(?, ?, ?, ?, ?, ?,?,?,?)";
            // 自動創立video_id
            String name = v.getName();
            String category = v.getCategory();
            String director = v.getDirector();
            String introduction = v.getIntroduction();
            String classification = v.getClassification();
            int vip = v.getVip();
            String coverpath = v.getCoverpath();
            String videopath = v.getVideopath();
            Date time = new Date();
            java.sql.Date update = new java.sql.Date(time.getTime());

            pres = conn.prepareStatement(sql);
            pres.setString(1, name);
            pres.setString(2, category);
            pres.setString(3, director);
            pres.setString(4, introduction);
            pres.setString(5, classification);
            pres.setInt(6, vip);
            pres.setString(7, coverpath);
            pres.setString(8, videopath);
            pres.setDate(9, update);

            row = pres.executeUpdate();

            exexcute_sql = pres.toString();
            System.out.print(exexcute_sql);

        } catch (SQLException e) {

            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBMgr.close(pres, conn);
        }

        long end_time = System.nanoTime();
        long duration = (end_time - start_time);

        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("time", duration);
        response.put("row", row);

        return response;

    }

    // 刪除影片
    public JSONObject deleteByID(int id) {
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();

            /** SQL指令 */
            String sql = "DELETE FROM `sa`.`video` WHERE `video_id` = ? LIMIT 1";

            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);
            /** 執行刪除之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }

        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);

        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);

        return response;
    }

    // 更新影片
    public JSONObject update(Video v) {
        /** 紀錄回傳之資料 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "Update `sa`.`video` SET `video_name` = ? ,`video_category` = ? , `director` = ?, "
                    + "`introduction` = ? ,`classification` = ? ,`vip` = ?,`cover_path` = ? ,`video_path` = ?,`video_update` = ?"
                    + "WHERE `video_id` = ?";
            /** 取得所需之參數 */
            String name = v.getName();
            String category = v.getCategory();
            String director = v.getDirector();
            String introduction = v.getIntroduction();
            String classification = v.getClassification();
            int vip = v.getVip();
            String coverpath = v.getCoverpath();
            String videopath = v.getVideopath();
            Date time = new Date();
            java.sql.Date update = new java.sql.Date(time.getTime());
            int id = v.getID();

            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, name);
            pres.setString(2, category);
            pres.setString(3, director);
            pres.setString(4, introduction);
            pres.setString(5, classification);
            pres.setInt(6, vip);
            pres.setString(7, coverpath);
            pres.setString(8, videopath);
            pres.setDate(9, update);
            pres.setInt(10, id);
            /** 執行更新之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(pres, conn);
        }

        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);

        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }

    // 檢查是否重複上傳影片
    public boolean checkDuplicate(Video v) {
        /** 紀錄SQL總行數，若為「-1」代表資料庫檢索尚未完成 */
        int row = -1;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT count(*) FROM `sa`.`video` WHERE `video_name` = ?";

            /** 取得所需之參數 */
            String name = v.getName();

            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, name);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 讓指標移往最後一列，取得目前有幾行在資料庫內 */
            rs.next();
            row = rs.getInt("count(*)");
            System.out.print(row);

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }

        /**
         * 判斷是否已經有一筆該電子郵件信箱之資料 若無一筆則回傳False，否則回傳True
         */
        return (row == 0) ? false : true;
    }

}
