package com.mes.domain;
import java.util.List;
public class Product {
    //货品id
    private int id;
    //货品名称
    private String name;
    //货品来源
    private String source;
    // 来源厂家
    private String  manufacturer;
    //销售地
    private String placeSale;
    //货品单价价格
    private double price;
    //货品保质期截止日期（格式：年-月）
    private String shelfLife;
    //库存数量
    private int quantityNum;
    //上架时间（默认为now()）
    private String addTime;
    //货品发布时间
    private String publishTime;
    //发布状态（1:已发布；0：未发布；默认为0）
    private int publishState;
    //更新时间（默认为now()）
    private String updateTime;
    //评价数据集合
   private List<ProductEvaluateInfo> evaluateList;
   //商品描述
    private String description;
    //商品图片
    private String imgSrc;

    // 好评率（从tb_product_comment_stats表获取）
    private double goodCommentRate;

    public double getGoodCommentRate() {
        return goodCommentRate;
    }
    public void setGoodCommentRate(double goodCommentRate) {
        this.goodCommentRate = goodCommentRate;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public List<ProductEvaluateInfo> getEvaluateList() {
        return evaluateList;
    }

    public void setEvaluateList(List<ProductEvaluateInfo> evaluateList) {
        this.evaluateList = evaluateList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPlaceSale() {
        return placeSale;
    }

    public void setPlaceSale(String placeSale) {
        this.placeSale = placeSale;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }

    public int getQuantityNum() {
        return quantityNum;
    }

    public void setQuantityNum(int quantityNum) {
        this.quantityNum = quantityNum;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public int getPublishState() {
        return publishState;
    }

    public void setPublishState(int publishState) {
        this.publishState = publishState;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
