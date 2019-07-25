package com.huwei.live.pojo;

import java.util.Date;

public class Video {
    private Integer id;

    private String name;

    private String content;

    private String url;

    private String videopicture;

    private String clazz;

    private Integer recommend;

    private Date time;

    private Integer isalive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getVideopicture() {
        return videopicture;
    }

    public void setVideopicture(String videopicture) {
        this.videopicture = videopicture == null ? null : videopicture.trim();
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz == null ? null : clazz.trim();
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getIsalive() {
        return isalive;
    }

    public void setIsalive(Integer isalive) {
        this.isalive = isalive;
    }
}