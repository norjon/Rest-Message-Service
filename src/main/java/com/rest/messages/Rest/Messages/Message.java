package com.rest.messages.Rest.Messages;

public class Message {

    private final String message;
    private final String phoneNumber;

    public Message(String message, String phoneNumber) {
        this.message = message;
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
