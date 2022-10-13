package com.example.whatsapplite.Users;

public class MessageModel {
    String uid,message ;
    Long Timestamp ;

    public MessageModel(String uid, String message) {
        this.uid = uid;
        this.message = message;
    }

    public MessageModel(String uid, String message, Long timestamp) {
        this.uid = uid;
        this.message = message;
        Timestamp = timestamp;
    }
    public MessageModel(){}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Long timestamp) {
        Timestamp = timestamp;
    }
}
