package com.huwei.live.pojo;

public class Message {
    private Integer id;

    private Integer senderid;

    private Integer receiverid;

    private String content;

    private Integer isalive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenderid() {
        return senderid;
    }

    public void setSenderid(Integer senderid) {
        this.senderid = senderid;
    }

    public Integer getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(Integer receiverid) {
        this.receiverid = receiverid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getIsalive() {
        return isalive;
    }

    public void setIsalive(Integer isalive) {
        this.isalive = isalive;
    }
}