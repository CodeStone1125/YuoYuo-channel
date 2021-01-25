package app;

import org.json.*;
import java.util.Date;

public class Order {
    /** id，訂單編號 */
    private int id;
    /** memberid，會員編號 */
    private int memberid;
    /** type，方案 */
    private String type;
    /** payment，付款方式 */
    private String payment;
    /** price，價錢 */
    private int price;
    /** phone，會員手機 */
    private Date paytime;
    /** status，訂單狀態，1是active */
    private int status;

    private OrderHelper oh = OrderHelper.getHelper();

    /**
     * 實例化（Instantiates）一個新的（new）Order 物件<br>
     * 採用多載（overload）方法進行，此建構子用於修改訂單資料時，新改資料庫已存在的訂單
     *
     * @param id       會員名
     * @param memberid 會員編號
     * @param type     會員電子信箱
     * @param payment  會員地址
     * @param price    會員姓名
     * @param paytime  會員購買時間
     * @param status   會員訂單狀況
     */
    public Order(int id, int memberid, String type, String payment, int price, Date paytime, int status) {
        this.id = id;
        this.memberid = memberid;
        this.type = type;
        this.payment = payment;
        this.price = price;
        this.paytime = paytime;
        this.status = status;
    }

    /**
     * 實例化（Instantiates）一個新的（new）Order 物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立訂單資料時，產生一個新的訂單
     *
     * @param memberid 會員編號
     * @param type     會員電子信箱
     * @param payment  會員地址
     * @param price    會員姓名
     */
    public Order(int memberid, String type, String payment, int price) {
        this.memberid = memberid;
        this.type = type;
        this.payment = payment;
        this.price = price;
    }

    /**
     * @param status 訂單狀態
     */
    public Order(int memberid, int status) {
        this.memberid = memberid;
        this.status = status;
    }

    /**
     * 取得訂單之編號
     *
     * @return the id 回傳訂單編號
     */
    public int getID() {
        return this.id;
    }

    /**
     * 取得會員之編號
     *
     * @return the memberid 回傳會員之編號
     */
    public int getMemberid() {
        return this.memberid;
    }

    /**
     * 取得會員之訂單方案
     *
     * @return the type 回傳訂單方案
     */
    public String getType() {
        return this.type;
    }

    /**
     * 取得會員之付款方式
     *
     * @return the payment 回傳會員之付款方式
     */
    public String getPayment() {
        return this.payment;
    }

    /**
     * 取得訂單價格
     *
     * @return the price 回傳訂單之價格
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * 取得訂單之付款時間
     *
     * @return the paytime 回傳訂單付款時間
     */
    public Date getPaytime() {
        return this.paytime;
    }

    /**
     * 取得訂單之狀態
     *
     * @return the status 回傳訂單狀態
     */
    public int getStatus() {
        return this.status;
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
        jso.put("memberid", getMemberid());
        jso.put("type", getType());
        jso.put("payment", getPayment());
        jso.put("price", getPrice());
        jso.put("paytime", getPaytime());
        jso.put("status", getStatus());

        return jso;
    }

    /*
     * 更新訂單資料
     *
     * @return the JSON object 回傳SQL更新之結果與相關封裝之資料
     */
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();

        /** 檢查該名會員是否已經在資料庫 */
        if (this.id != 0) {
            /** 若有則將目前更新後之資料更新至資料庫中 */
            /** 透過MemberHelper物件，更新目前之會員資料置資料庫中 */
            data = oh.updatestatus(this);
        }

        return data;
    }
}
