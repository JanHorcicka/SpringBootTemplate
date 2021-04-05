package com.ipdive.springboottemplate.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@DynamoDBTable(tableName="PasswordResetTokens")
public class PasswordResetToken {

    @Transient
    private final int EXPIRATION = 60; // in minutes

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
        this.expirationDateEpoch = LocalDate.now().plusDays(1).toEpochDay();
    }

    public PasswordResetToken(String id, String username) {
        this.id = id;
        this.user = username;
        LocalDateTime timeNow = LocalDateTime.now();
        timeNow.plusMinutes(EXPIRATION);
        this.expirationDateEpoch = timeNow.toEpochSecond(ZoneOffset.UTC);
    }

    public PasswordResetToken(String id, String user, int expirationInMinutes) {
        this.id = id;
        this.user = user;
        LocalDateTime timeNow = LocalDateTime.now();
        timeNow.plusMinutes(expirationInMinutes);
        this.expirationDateEpoch = timeNow.toEpochSecond(ZoneOffset.UTC);
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