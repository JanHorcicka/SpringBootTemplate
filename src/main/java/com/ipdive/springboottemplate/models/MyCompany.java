package com.ipdive.springboottemplate.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * This is an example model connected to a DynamoDB Table.
 * Please modify the class according to your business needs.
 * Make sure to keep the empty constructor which is used by Spring.
 * The one-parameter constructor is to ease up testing.
 */

@DynamoDBTable(tableName="Companies")
public class MyCompany {

    private String ticker;
    private String name;
    private String sector;
    private String industryCategory;
    private String industryGroup;
    private String country;
    private String weburl;
    private String currency;

    public MyCompany() {

    }

    public MyCompany(String ticker) {
        this.ticker = ticker;
        this.name = "Test";
        this.sector = "Test";
        this.industryCategory = "Test";
        this.industryGroup = "Test";
        this.country = "Test";
        this.weburl = "Test";
    }

    public MyCompany(String ticker, String name, String country, String industryCategory, String weburl, String currency) {
        this.ticker = ticker;
        this.name = name;
        this.industryCategory = industryCategory;
        this.country = country;
        this.weburl = weburl;
        this.currency = currency;
    }

    @DynamoDBHashKey
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute
    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    @DynamoDBAttribute
    public String getIndustryCategory() {
        return industryCategory;
    }

    public void setIndustryCategory(String industryCategory) {
        this.industryCategory = industryCategory;
    }

    @DynamoDBAttribute
    public String getIndustryGroup() {
        return industryGroup;
    }

    public void setIndustryGroup(String industryGroup) {
        this.industryGroup = industryGroup;
    }

    @DynamoDBAttribute
    public String getCountry() {
        return country;
    }

    @DynamoDBAttribute
    public String getWeburl() {
        return weburl;
    }

    @DynamoDBAttribute
    public String getCurrency() {
        return currency;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
