package com.slidetonew;

import com.google.gson.annotations.SerializedName;
import java.util.List;

class NewsBean {
    private List<BBM54PGAwangning> BBM54PGAwangning;

    List<BBM54PGAwangning> getBbm54PGAwangnings() {
        return this.BBM54PGAwangning;
    }
}
class BBM54PGAwangning{
    /*
    * "liveInfo":null,
      "docid":"F9MKJGVI05503FCU",
      "source":"纵相新闻",
      "title":"外媒：朝鲜709人检测病毒2.5万人隔离 保持0确诊",
      "priority":101,
      "hasImg":1,
      "url":"",
      "skipURL":"http://3g.163.com/ntes/special/00340EPA/wapSpecialModule.html?sid=S1580527844281",
      "specialID":"S1580527844281",
      "commentCount":4622,
      "imgsrc3gtype":"1",
      "stitle":"S1580527844281",
      "digest":"东方网·纵相新闻记者单珊据路透社当地时间4月8日报道，朝鲜已",
      "skipType":"special",
      "imgsrc":"http://cms-bucket.ws.126.net/2020/0408/f55122c9j00q8gfse001ic000s600e3c.jpg",
      "ptime":"2020-04-08 13:35:23"
      * */
    @SerializedName("liveInfo")
    private String liveInfo;
    @SerializedName("docid")
    private String docid;
    @SerializedName("source")
    private String source;
    @SerializedName("title")
    private String title;
    private String priority;
    private String hasImg;
    @SerializedName("url")
    private String url;
    private String skipURL;
    private String specialID;
    private String imgsrc3gtype;
    private String stitle;
    private String digest;
    private String skipType;
    @SerializedName("imgsrc")
    private String imgsrc;
    private String ptime;
    private String commentCount;

    public String getCommentCount() {
        return commentCount;
    }

    public String getLiveInfo() {
        return liveInfo;
    }

    public void setLiveInfo(String liveInfo) {
        this.liveInfo = liveInfo;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getHasImg() {
        return hasImg;
    }

    public void setHasImg(String hasImg) {
        this.hasImg = hasImg;
    }

    public String getSkipURL() {
        return skipURL;
    }

    public void setSkipURL(String skipURL) {
        this.skipURL = skipURL;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getSpecialID() {
        return specialID;
    }

    public void setSpecialID(String specialID) {
        this.specialID = specialID;
    }

    public String getImgsrc3gtype() {
        return imgsrc3gtype;
    }

    public void setImgsrc3gtype(String imgsrc3gtype) {
        this.imgsrc3gtype = imgsrc3gtype;
    }

    public String getStitle() {
        return stitle;
    }

    public void setStitle(String stitle) {
        this.stitle = stitle;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getSkipType() {
        return skipType;
    }

    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }
}
class imgextra{
    String imgsrc;

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }
}
