package com.mes.domain;
//评价数据子表的实体类
public class ProductEvaluateInfo {
    //主键
    private int id;
    //主表的主键
    private  int mainId;
    //用户id
    private String userId;
    //用户评价信息
    private String evaluateInfo;
    //评价时间
    private String evaluateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMainId() {
        return mainId;
    }

    public void setMainId(int mainId) {
        this.mainId = mainId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEvaluateInfo() {
        return evaluateInfo;
    }

    public void setEvaluateInfo(String evaluateInfo) {
        this.evaluateInfo = evaluateInfo;
    }

    public String getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(String evaluateTime) {
        this.evaluateTime = evaluateTime;
    }
}
