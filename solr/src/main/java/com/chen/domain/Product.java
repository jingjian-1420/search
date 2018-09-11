package com.chen.domain;

import org.apache.solr.client.solrj.beans.Field;

import java.math.BigDecimal;
import java.util.List;

public class Product {
    @Field
    private String id;

    @Field(value = "name_s")
    private String name1;

    @Field(value = "aaaa")
    private String name2;

    @Field("show_type")
    private Integer showType;

    @Field("per_Price")
    private Double perPrice;

    @Field("commission")
    private Double commission;

    @Field("test_mu_value")
    private List<Double> testMuValue;

    @Field("test_mup_values")
    private List<String> testMupValues;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getPerPrice() {
        return perPrice;
    }

    public void setPerPrice(Double perPrice) {
        this.perPrice = perPrice;
    }

    public List<Double> getTestMuValue() {
        return testMuValue;
    }

    public void setTestMuValue(List<Double> testMuValue) {
        this.testMuValue = testMuValue;
    }

    public List<String> getTestMupValues() {
        return testMupValues;
    }

    public void setTestMupValues(List<String> testMupValues) {
        this.testMupValues = testMupValues;
    }
}
