package com.mycompany.mavenproject3;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;

//trigger github actions
public class MainApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        ArrayList<Message> sentMessages = new ArrayList<>();
        ArrayList<Message> disregardedMessages = new ArrayList<>();
        ArrayList<Message> storedMessages = loadStoredMessagesFromJson("src/main/resources/stored_messages.json");
        ArrayList<String> messageHashes = new ArrayList<>();
        ArrayList<String> messageIDs = new ArrayList<>();

        // Login
        System.out.println("LOGIN TO QUICKCHAT");
        System.out.print("Enter username: ");
        String username = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();

        if (!username.equals("admin") || !password.equals("pass123")) {
            System.out.println("Invalid login. Exiting...");
            return;
        }

        System.out.println("Welcome to QuickChat!");

        // Menu loop
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Send Message");
            System.out.println("2. View Sent Messages");
            System.out.println("3. View Disregarded Messages");
            System.out.println("4. View Stored Messages");
            System.out.println("5. Display Longest Sent Message");
            System.out.println("6. Search by Message ID");
            System.out.println("7. Search by Recipient");
            System.out.println("8. Delete by Message Hash");
            System.out.println("9. Display Full Sent Report");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            String choice = input.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter recipient cell: ");
                    String cell = input.nextLine();
                    System.out.print("Enter message: ");
                    String msg = input.nextLine();
                    System.out.print("Flag (1=Sent, 2=Stored, 3=Disregard): ");
                    int flag = Integer.parseInt(input.nextLine());

                    Message message = new Message(generateMessageID(), cell, msg, flag);
                    String hash = message.getHashedMessage();
                    messageHashes.add(hash);
                    messageIDs.add(message.getMessageID());

                    if (flag == 1) {
                        sentMessages.add(message);
                    } else if (flag == 2) {
                        storedMessages.add(message);
                    } else if (flag == 3) {
                        disregardedMessages.add(message);
                    } else {
                        System.out.println("Invalid flag.");
                    }
                    break;

                case "2":
                    System.out.println("Sent Messages:");
                    for (Message m : sentMessages) {
                        System.out.println(m);
                    }
                    break;

                case "3":
                    System.out.println("Disregarded Messages:");
                    for (Message m : disregardedMessages) {
                        System.out.println(m);
                    }
                    break;

                case "4":
                    System.out.println("Stored Messages:");
                    for (Message m : storedMessages) {
                        System.out.println(m);
                    }
                    break;

                case "5":
                    Message longest = null;
                    for (Message m : sentMessages) {
                        if (longest == null || m.getMessage().length() > longest.getMessage().length()) {
                            longest = m;
                        }
                    }
                    if (longest != null) {
                        System.out.println("Longest Message: " + longest.getMessage());
                    } else {
                        System.out.println("No messages sent yet.");
                    }
                    break;

                case "6":
                    System.out.print("Enter message ID to search: ");
                    String idSearch = input.nextLine();
                    boolean found = false;
                    for (Message m : sentMessages) {
                        if (m.getMessageID().equals(idSearch)) {
                            System.out.println("Found: " + m);
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("Message ID not found.");
                    }
                    break;

                case "7":
                    System.out.print("Enter recipient to search: ");
                    String recipientSearch = input.nextLine();
                    for (Message m : sentMessages) {
                        if (m.getRecipientCell().equals(recipientSearch)) {
                            System.out.println(m);
                        }
                    }
                    break;

                case "8":
                    System.out.print("Enter message hash to delete: ");
                    String hashToDelete = input.nextLine();
                    sentMessages.removeIf(m -> m.getHashedMessage().equals(hashToDelete));
                    System.out.println("Message (if existed) deleted.");
                    break;

                case "9":
                    System.out.println("==== Sent Messages Report ====");
                    for (Message m : sentMessages) {
                        System.out.println("ID: " + m.getMessageID());
                        System.out.println("Recipient: " + m.getRecipientCell());
                        System.out.println("Message: " + m.getMessage());
                        System.out.println("Hash: " + m.getHashedMessage());
                        System.out.println("-----------------------------");
                    }
                    break;

                case "0":
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    public static String generateMessageID() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static ArrayList<Message> loadStoredMessagesFromJson(String filePath) {
        ArrayList<Message> messages = new ArrayList<>();
        try {
            Gson gson = new Gson();
            Reader reader = new FileReader(filePath);
            Type messageListType = new TypeToken<ArrayList<Message>>(){}.getType();
            messages = gson.fromJson(reader, messageListType);
            reader.close();
        } catch (Exception e) {
            System.out.println("Error loading JSON: " + e.getMessage());
        }
        return messages;
    }
}