package com.ipdive.springboottemplate.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import javax.persistence.Transient;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@DynamoDBTable(tableName="PasswordResetTokens")
public class PasswordResetToken {

    @Transient
    private final int TTL_DAYS = 1; // in days

    @Transient
    private boolean isExpired = false;
    private long expirationDateEpoch;
    private String id;
    private String user;

    public PasswordResetToken() {

    }

    public PasswordResetToken(User user) {
        this.id = UUID.randomUUID().toString();
        this.user = user.getUsername();
        this.expirationDateEpoch = Instant.now().plus(TTL_DAYS, ChronoUnit.DAYS).getEpochSecond();
    }

    public PasswordResetToken(String id, String username) {
        this.id = id;
        this.user = username;
        this.expirationDateEpoch = Instant.now().plus(TTL_DAYS, ChronoUnit.DAYS).getEpochSecond();
    }

    @DynamoDBHashKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @DynamoDBAttribute
    public long getExpirationDateEpoch() {
        return expirationDateEpoch;
    }

    public void setExpirationDateEpoch(long expirationDateEpoch) {
        this.expirationDateEpoch = expirationDateEpoch;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }
}