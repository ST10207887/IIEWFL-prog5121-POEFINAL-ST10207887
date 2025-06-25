package com.mycompany.mavenproject3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Message {
    private String messageID;
    private String recipientCell;
    private String message;
    private int flag;
    private String hashedMessage;

    // Default constructor (needed for Gson)
    public Message() {
    }

    // Constructor
    public Message(String messageID, String recipientCell, String message, int flag) {
        this.messageID = messageID;
        this.recipientCell = recipientCell;
        this.message = message;
        this.flag = flag;
        this.hashedMessage = createMessageHash();
    }

    // Getters
    public String getMessageID() {
        return messageID;
    }

    public String getRecipientCell() {
        return recipientCell;
    }

    public String getMessage() {
        return message;
    }

    public int getFlag() {
        return flag;
    }

    public String getHashedMessage() {
        return hashedMessage;
    }

    // Hashing logic 
    private String createMessageHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String input = messageID + recipientCell + message;
            byte[] hashBytes = digest.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not supported", e);
        }
    }

    @Override
    public String toString() {
        return "Message ID: " + messageID +
                "\nRecipient: " + recipientCell +
                "\nMessage: " + message +
                "\nFlag: " + flag +
                "\nHash: " + hashedMessage + "\n";
    }
}

