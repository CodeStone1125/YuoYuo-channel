package app;

import org.json.*;
import java.util.Date;

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

public class Member {

    /** id，會員編號 */
    private int Id;
    private String Firstname;
    private String Lastname;
    private String Email;
    /** password，會員密碼 */
    private String Password;
    private String Phonenumber;
    private int Level;
    private Date Expiretime;

    /** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private MemberHelper mh = MemberHelper.getHelper();

    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立會員資料時，產生一名新的會員
     *
     * @param id
     * @param firstname
     * @param lastname
     * @param email
     * @param password
     * @param phonenumber
     * @param level
     * @param expiretime
     */
    public Member(int id, String firstname, String lastname, String email, String password, String phonenumber,
            int level, Date expiretime) {
        this.Id = id;
        this.Email = email;
        this.Password = password;
        this.Firstname = firstname;
        this.Lastname = lastname;
        this.Password = password;
        this.Phonenumber = phonenumber;
        this.Level = level;
        this.Expiretime = expiretime;
    }

    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於更新會員資料時，產生一名會員同時需要去資料庫檢索原有更新時間分鐘數與會員組別
     *
     * 
     * @param firstname
     * @param lastname
     * @param email
     * @param password
     * @param phonenumber
     */
    // 一樣是呼叫member，但是這次是沒有id的版本
    public Member(String firstname, String lastname, String email, String password, String phonenumber) {
        this.Email = email;
        this.Password = password;
        this.Firstname = firstname;
        this.Lastname = lastname;
        this.Phonenumber = phonenumber;
    }

    // 一樣是呼叫member，但是這次是沒有id的版本
    /**
     *
     * @param email
     * @param password
     */
    public Member(String email, String password) {
        this.Email = email;
        this.Password = password;
    }

    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於更新會員資料時，產生一名會員同時需要去資料庫檢索原有更新時間分鐘數與會員組別
     *
     * @param id
     * @param firstname
     * @param lastname
     * @param email
     * @param password
     * @param phonenumber
     */
    // 一樣是呼叫member，但是這次是沒有id的版本
    public Member(int id, String firstname, String lastname, String password, String phonenumber) {
        this.Id = id;
        this.Password = password;
        this.Firstname = firstname;
        this.Lastname = lastname;
        this.Password = password;
        this.Phonenumber = phonenumber;
    }

    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於更新會員資料時，產生一名會員同時需要去資料庫檢索原有更新時間分鐘數與會員組別
     *
     * @param id
     */
    // 一樣是呼叫member，但是這次是id的版本
    public Member(int id) {
        this.Id = id;
    }

    /**
     * 取得會員之編號
     *
     * @return the id 回傳會員編號
     */
    public int getID() {
        return this.Id;
    }

    /**
     * 取得會員之電子郵件信箱
     *
     * @return the email 回傳會員電子郵件信箱
     */
    public String getEmail() {
        return this.Email;
    }

    /**
     * 取得會員之密碼
     *
     * @return the password 回傳會員密碼
     */
    public String getPassword() {
        return this.Password;
    }

    /**
     * 取得會員之姓氏
     *
     * @return the firstname 回傳會員姓氏
     */
    public String getFirstname() {
        return this.Firstname;
    }

    /**
     * 取得會員之名字
     *
     * @return the lastname 回傳會員名字
     */
    public String getLastname() {
        return this.Lastname;
    }

    /**
     * 取得會員之電話
     *
     * @return the phone 回傳會員電話
     */
    public String getPhonenumber() {
        return this.Phonenumber;
    }

    /**
     * 取得會員之級別
     *
     * @return the level 回傳會員級別
     */
    public int getLevel() {
        return this.Level;
    }

    /**
     * 取得會員之到期日
     *
     * @return the level 回傳會員到期日
     */
    public Date getExpiretime() {
        return this.Expiretime;
    }

    /**
     * 取得該名會員所有資料
     *
     * @return the data 取得該名會員之所有資料並封裝於JSONObject物件內
     */
    public JSONObject getData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝 */
        JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("firstname", getFirstname());
        jso.put("lastname", getLastname());
        jso.put("email", getEmail());
        jso.put("password", getPassword());
        jso.put("phonenumber", getPhonenumber());
        jso.put("level", getLevel());
        jso.put("expiretime", getExpiretime());

        return jso;
    }

    /**
     * 取得資料庫內之更新資料時間分鐘數與會員組別
     *
     */
    /**
     * private void getLoginTimesStatus() { /**
     * 透過MemberHelper物件，取得儲存於資料庫的更新時間分鐘數與會員組別
     */
    /**
     * JSONObject data = mh.getLoginTimesStatus(this); /**
     * 將資料庫所儲存該名會員之相關資料指派至Member物件之屬性
     */
    /**
     * this.login_times = data.getInt("login_times"); this.status =
     * data.getString("status"); }
     */
    /**
     * 更新會員資料
     *
     * @return the JSON object 回傳SQL更新之結果與相關封裝之資料
     */
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();

        /** 檢查該名會員是否已經在資料庫 */
        if (this.Id != 0) {
            /** 若有則將目前更新後之資料更新至資料庫中 */
            /** 透過MemberHelper物件，更新目前之會員資料置資料庫中 */
            data = mh.update(this);
        }

        return data;
    }

}