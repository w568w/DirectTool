package ml.qingsu.test;


public class MusicNode {

    public MusicNode(String imei, int good, String des, String name,
                     String data, String objectId) {
        this.imei = imei;
        this.good = good;
        this.des = des;
        this.name = name;
        this.date = data;
        this.objectId = objectId;
    }

    public MusicNode() {
        // TODO Auto-generated constructor stub
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //发送机的唯一标识码
    private String imei;

    public String getImei() {
        return this.imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getGood() {
        return this.good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public String getDes() {
        return this.des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //点赞数
    private int good;
    //描述
    private String des;
    //歌名
    private String name;
    //发出时间
    private String date;
    //ID
    public String objectId;
    //HASH
    public String hash;

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
