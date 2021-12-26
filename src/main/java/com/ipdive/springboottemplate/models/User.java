package com.ipdive.springboottemplate.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@DynamoDBTable(tableName = "Users")
public class User {

    private String username;
    private String password;
    private boolean isEnabled;
    private String authorities;
    private boolean isPremium;
    private long premiumExpiryDateEpoch;
    private long dateCreatedEpoch;
    private String referral;

    public User() {

    }

    public User(String username) {
        this.username = username;
        this.password = "pass";
        this.isEnabled = false;
        this.authorities = "USER";
        this.isPremium = false;
        this.premiumExpiryDateEpoch = Instant.now().plus(30, ChronoUnit.DAYS).getEpochSecond();
        this.dateCreatedEpoch = Instant.now().getEpochSecond();
    }

    public User(String username, String password, String referral) {
        this.username = username;
        this.password = password;
        this.isEnabled = false;
        this.isPremium = false;
        this.dateCreatedEpoch = Instant.now().getEpochSecond();
        this.referral = referral;
    }

    public User(String username, String password, boolean isEnabled, String authorities, boolean isPremium, long premiumExpiryDateEpoch, long dateCreatedEpoch) {
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
        this.authorities = authorities;
        this.isPremium = isPremium;
        this.premiumExpiryDateEpoch = premiumExpiryDateEpoch;
        this.dateCreatedEpoch = dateCreatedEpoch;
    }

    @DynamoDBHashKey
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @DynamoDBAttribute
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @DynamoDBAttribute
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @DynamoDBAttribute
    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    @DynamoDBAttribute
    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean isPremium) {
        this.isPremium = isPremium;
    }

    @DynamoDBAttribute
    public long getPremiumExpiryDateEpoch() {
        return premiumExpiryDateEpoch;
    }

    public void setPremiumExpiryDateEpoch(long premiumExpiryDateEpoch) {
        this.premiumExpiryDateEpoch = premiumExpiryDateEpoch;
    }

    @DynamoDBAttribute
    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    @DynamoDBAttribute
    public long getDateCreatedEpoch() {
        return dateCreatedEpoch;
    }

    public void setDateCreatedEpoch(long dateCreatedEpoch) {
        this.dateCreatedEpoch = dateCreatedEpoch;
    }
}
