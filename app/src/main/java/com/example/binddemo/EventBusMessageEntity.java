package com.example.binddemo;

/**
 * Created by xue on 2019/1/21.
 */

public class EventBusMessageEntity {
    private String message;
    public EventBusMessageEntity(String messge){
        this.message = messge;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
