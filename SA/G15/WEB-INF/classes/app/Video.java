package app;

import java.util.Date;
import org.json.*;

public class Video {

    /** id，會員編號 */
    private int Id;
    private String Name;
    private String Category;
    private String Director;
    private String Introduction;
    private String Classification;
    private int Vip;
    private String Coverpath;
    private String Videopath;
    private Date Update;

    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於新增產品時
     *
     * @param id 產品編號
     */
    public Video(int id, String name, String category, String director, String introduction, String classification,
            int vip, String coverpath, String videopath, Date update) {
        this.Id = id;
        this.Name = name;
        this.Category = category;
        this.Director = director;
        this.Introduction = introduction;
        this.Classification = classification;
        this.Vip = vip;
        this.Coverpath = coverpath;
        this.Videopath = videopath;
        this.Update = update;
    }

    // 這個沒有update
    public Video(int id, String name, String category, String director, String introduction, String classification,
            int vip, String coverpath, String videopath) {
        this.Id = id;
        this.Name = name;
        this.Category = category;
        this.Director = director;
        this.Introduction = introduction;
        this.Classification = classification;
        this.Vip = vip;
        this.Coverpath = coverpath;
        this.Videopath = videopath;

    }

    // 這個沒有update和id
    public Video(String name, String category, String director, String introduction, String classification, int vip,
            String coverpath, String videopath) {
        this.Name = name;
        this.Category = category;
        this.Director = director;
        this.Introduction = introduction;
        this.Classification = classification;
        this.Vip = vip;
        this.Coverpath = coverpath;
        this.Videopath = videopath;

    }

    /**
     * 取得影片編號
     *
     * @return int 回傳影片編號
     */
    public int getID() {
        return this.Id;
    }

    /**
     * 取得產品名稱
     *
     * @return String 回傳產品名稱
     */
    public String getName() {
        return this.Name;
    }

    public String getCategory() {
        return this.Category;
    }

    public String getDirector() {
        return this.Director;
    }

    public String getIntroduction() {
        return this.Introduction;
    }

    public String getClassification() {
        return this.Classification;
    }

    public int getVip() {
        return this.Vip;
    }

    public String getCoverpath() {
        return this.Coverpath;
    }

    public String getVideopath() {
        return this.Videopath;
    }

    public Date getUpdate() {
        return this.Update;
    }

    /**
     * 取得產品資訊
     *
     * @return JSONObject 回傳產品資訊
     */
    public JSONObject getData() {
        /** 透過JSONObject將該項產品所需之資料全部進行封裝 */
        JSONObject jso = new JSONObject();
        jso.put("Id", getID());
        jso.put("Name", getName());
        jso.put("Category", getCategory());
        jso.put("Director", getDirector());
        jso.put("Introduction", getIntroduction());
        jso.put("Classification", getClassification());
        jso.put("Vip", getVip());
        jso.put("Coverpath", getCoverpath());
        jso.put("Videopath", getVideopath());
        jso.put("Update", getUpdate());

        return jso;
    }
}
