package app;

import org.json.*;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class Member Member類別（class）具有會員所需要之屬性與方法，並且儲存與會員相關之商業判斷邏輯<br>
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */
public class Videofavorite {
    /** id，會員編號 */
    private int FavoriteId;
    private int Memberid;
    private int Videoid;
    // private Video Video;
    private String Videoname;
    /** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    // private VideofavoriteHelper vfh = VideofavoriteHelper.getHelper();

    /**
     * @param id
     * @param memberid
     * @param videoid
     */
    public Videofavorite(int videofavoriteid, int videoid, int memberid, String video_name) {
        this.FavoriteId = videofavoriteid;
        this.Memberid = memberid;
        this.Videoid = videoid;
        this.Videoname = video_name;
    }

    /** 新增最愛影片時用 */
    public Videofavorite(int videoid, int memberid) {
        this.Memberid = memberid;
        this.Videoid = videoid;
    }

    /**
     * 取得會員之編號
     *
     * @return the id 回傳會員編號
     */
    public int getFavoriteId() {
        return this.FavoriteId;
    }

    /**
     * 取得會員之電子郵件信箱
     *
     * @return the email 回傳會員電子郵件信箱
     */
    public int getMemberid() {
        return this.Memberid;
    }

    /**
     * 取得會員之密碼
     *
     * @return the password 回傳會員密碼
     */
    public int getVideoid() {
        return this.Videoid;
    }

    public String getVideoname() {
        return this.Videoname;
    }

    /**
     * 取得該名會員所有資料
     *
     * @return the data 取得該名會員之所有資料並封裝於JSONObject物件內
     */
    public JSONObject getData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝 */
        JSONObject jso = new JSONObject();
        jso.put("FavoriteId", getFavoriteId());
        jso.put("Videoid", getVideoid());
        jso.put("Memberid", getMemberid());
        jso.put("Videoname", getVideoname());

        return jso;
    }

}