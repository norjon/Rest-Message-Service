package com.rest.messages.Rest.Messages;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MessageEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private long timestamp;

    private String phoneNumber;
 
    private String message;

    private boolean hasBeenRead;
 
    public MessageEntity() {
    }
 
    public MessageEntity(String phoneNumber, String message, boolean hasBeenRead) {
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.hasBeenRead = hasBeenRead;
        this.timestamp = System.currentTimeMillis();
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isHasBeenRead() {
        return hasBeenRead;
    }

    public void setHasBeenRead(boolean hasBeenRead) {
        this.hasBeenRead = hasBeenRead;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }



}