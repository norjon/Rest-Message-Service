package com.rest.messages.Rest.Messages;

public class DeleteMessage {

    private String message;
    private String phoneNumber;
    private Long timestamp;

    public DeleteMessage(Long timestamp, String message, String phoneNumber) {
        this.message = message;
        this.phoneNumber = phoneNumber;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
