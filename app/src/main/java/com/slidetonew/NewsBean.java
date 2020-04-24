package com.slidetonew;

import com.google.gson.annotations.SerializedName;
import java.util.List;

class NewsBean {
    //头条
    private List<NewsItem> BBM54PGAwangning;
    //娱乐
    private List<NewsItem> BA10TA81wangning;
    //体育
    private List<NewsItem> BA8E6OEOwangning;
    //财经
    private List<NewsItem> BA8EE5GMwangning;
    //军事
    private List<NewsItem> BAI67OGGwangning;
    //科技
    private List<NewsItem> BA8D4A3Rwangning;
    //手机
    private List<NewsItem> BAI6I0O5wangning;
    //数码
    private List<NewsItem> BAI6JOD9wangning;
    //时尚
    private List<NewsItem> BA8F6ICNwangning;
    //游戏
    private List<NewsItem> BAI6RHDKwangning;
    //教育
    private List<NewsItem> BA8FF5PRwangning;
    //健康
    private List<NewsItem> BDC4QSV3wangning;
    //旅游
    private List<NewsItem> BEO4GINLwangning;

    public List<NewsItem> getBBM54PGAwangning() {
        return BBM54PGAwangning;
    }

    public List<NewsItem> getBA10TA81wangning() {
        return BA10TA81wangning;
    }

    public List<NewsItem> getBA8E6OEOwangning() {
        return BA8E6OEOwangning;
    }

    public List<NewsItem> getBA8EE5GMwangning() {
        return BA8EE5GMwangning;
    }

    public List<NewsItem> getBAI67OGGwangning() {
        return BAI67OGGwangning;
    }

    public List<NewsItem> getBA8D4A3Rwangning() {
        return BA8D4A3Rwangning;
    }

    public List<NewsItem> getBAI6I0O5wangning() {
        return BAI6I0O5wangning;
    }

    public List<NewsItem> getBAI6JOD9wangning() {
        return BAI6JOD9wangning;
    }

    public List<NewsItem> getBA8F6ICNwangning() {
        return BA8F6ICNwangning;
    }

    public List<NewsItem> getBAI6RHDKwangning() {
        return BAI6RHDKwangning;
    }

    public List<NewsItem> getBA8FF5PRwangning() {
        return BA8FF5PRwangning;
    }

    public List<NewsItem> getBDC4QSV3wangning() {
        return BDC4QSV3wangning;
    }

    public List<NewsItem> getBEO4GINLwangning() {
        return BEO4GINLwangning;
    }
}
class NewsItem {
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
