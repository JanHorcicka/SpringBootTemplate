package com.ipdive.springboottemplate.models;

import com.ipdive.springboottemplate.logic.DateTimeZoneManager;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class UserInfo {

    private String username;
    private long dateCreatedEpoch;

    public UserInfo() {

    }

    public UserInfo(String username, long dateCreatedEpoch) {
        this.username = username;
        this.dateCreatedEpoch = dateCreatedEpoch;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getDateCreatedEpoch() {
        return dateCreatedEpoch;
    }

    public void setDateCreatedEpoch(long dateCreatedEpoch) {
        this.dateCreatedEpoch = dateCreatedEpoch;
    }

    public String getDateCreatedString() {
        DateTimeFormatter dateTimeFormatter = DateTimeZoneManager.getDateTimeFormatter();
        return dateTimeFormatter.format(Instant.ofEpochSecond(this.dateCreatedEpoch));
    }
}
