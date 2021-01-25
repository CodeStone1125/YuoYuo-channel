package app;

import java.sql.*;
import org.json.*;
import util.DBMgr;

public class VideofavoriteHelper {
    private VideofavoriteHelper() {
    }

    private static VideofavoriteHelper vfh;
    private Connection conn = null;
    private PreparedStatement pres = null;
    private VideoHelper vh = VideoHelper.getHelper();

    /**
     * 靜態方法<br>
     * 實作Singleton（單例模式），僅允許建立一個MemberHelper物件
     *
     * @return the helper 回傳MemberHelper物件
     */
    public static VideofavoriteHelper getHelper() {
        /** Singleton檢查是否已經有MemberHelper物件，若無則new一個，若有則直接回傳 */
        if (vfh == null)
            vfh = new VideofavoriteHelper();

        return vfh;
    }

    public JSONObject create(Videofavorite videofavorite) {
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
            String sql = "INSERT INTO `sa`.`favorite_video`(`video_ID`, `member_ID`)" + "VALUES(?, ?)";

            /** 取得所需之參數 */
            int video_ID = videofavorite.getVideoid();
            int member_ID = videofavorite.getMemberid();

            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, video_ID);
            pres.setInt(2, member_ID);

            /** 執行新增之SQL指令並記錄影響之行數 */
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
        response.put("time", duration);
        response.put("row", row);

        return response;
    }
    public boolean checkDuplicate(Videofavorite vf) {
        /** 紀錄SQL總行數，若為「-1」代表資料庫檢索尚未完成 */
        int row = -1;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT count(*) FROM `sa`.`favorite_video` WHERE video_ID = ? && member_ID = ?";
            /** 取得所需之參數 */
            int vid = vf.getVideoid();
            int mid = vf.getMemberid();
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, vid);
            pres.setInt(2, mid);
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
            String sql = "DELETE FROM `sa`.`favorite_video` WHERE `favorite_video_ID` = ? LIMIT 1";

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



    public JSONObject getByID(int memberid) {
        /** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
        Videofavorite vf = null;
        /** 用於儲存所有檢索回之會員，以JSONArray方式儲存 */
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
            String sql = "SELECT * FROM `sa`.`favorite_video` JOIN `sa`.`video` ON `sa`.`favorite_video`.`video_id` = `sa`.`video`.`video_id` WHERE member_id = ?";

            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, memberid);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            /** 正確來說資料庫只會有一筆該會員編號之資料，因此其實可以不用使用 while 迴圈 */
            while (rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;

                /** 將 ResultSet 之資料取出 */
                int video_favorite_ID = rs.getInt("favorite_video_ID");
                int video_ID = rs.getInt("video_ID");
                int member_ID = rs.getInt("member_ID");
                String video_name = rs.getString("video_name");
                /** 將每一筆會員資料產生一名新Member物件 */
                vf = new Videofavorite(video_favorite_ID, video_ID, member_ID, video_name);
                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
                jsa.put(vf.getData());
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

    // public JSONObject deleteByID(int video_favorite_id) {
    // /** 記錄實際執行之SQL指令 */
    // String exexcute_sql = "";
    // /** 紀錄程式開始執行時間 */
    // long start_time = System.nanoTime();
    // /** 紀錄SQL總行數 */
    // int row = 0;
    // /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
    // ResultSet rs = null;

    // try {
    // /** 取得資料庫之連線 */
    // conn = DBMgr.getConnection();

    // /** SQL指令 */
    // String sql = "DELETE FROM `sa`.`favorite_video` WHERE favorite_video_ID = ?
    // LIMIT 1";

    // /** 將參數回填至SQL指令當中 */
    // pres = conn.prepareStatement(sql);
    // pres.setInt(1, video_favorite_id);
    // /** 執行刪除之SQL指令並記錄影響之行數 */
    // row = pres.executeUpdate();

    // /** 紀錄真實執行的SQL指令，並印出 **/
    // exexcute_sql = pres.toString();
    // System.out.println(exexcute_sql);

    // } catch (SQLException e) {
    // /** 印出JDBC SQL指令錯誤 **/
    // System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(),
    // e.getMessage());
    // } catch (Exception e) {
    // /** 若錯誤則印出錯誤訊息 */
    // e.printStackTrace();
    // } finally {
    // /** 關閉連線並釋放所有資料庫相關之資源 **/
    // DBMgr.close(rs, pres, conn);
    // }

    // /** 紀錄程式結束執行時間 */
    // long end_time = System.nanoTime();
    // /** 紀錄程式執行時間 */
    // long duration = (end_time - start_time);

    // /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
    // JSONObject response = new JSONObject();
    // response.put("sql", exexcute_sql);
    // response.put("row", row);
    // response.put("time", duration);

    // return response;
    // }
}