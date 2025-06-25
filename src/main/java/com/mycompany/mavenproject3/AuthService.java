package com.mycompany.mavenproject3;

import java.io.*;

public class AuthService {
    private static final String FILE_PATH = "users.txt";

    public static boolean register(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.getUsername() + "," +
                         user.getPassword() + "," +
                         user.getCellNumber() + "," +
                         user.getFirstName() + "," +
                         user.getLastName());
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
            return false;
        }
    }

    public static User login(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    if (parts[0].equals(username) && parts[1].equals(password)) {
                        return new User(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user file: " + e.getMessage());
        }
        return null;
    }
}
