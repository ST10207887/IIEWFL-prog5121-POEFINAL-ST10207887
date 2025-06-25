package com.mycompany.mavenproject3;
public class User {
    private final String username;
    private final String password;
    private final String cellNumber;
    private final String firstName;
    private final String lastName;

    public User(String username, String password, String cellNumber, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getCellNumber() { return cellNumber; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    public static boolean isUsernameValid(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public static boolean isPasswordComplex(String password) {
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*[0-9].*") &&
               password.matches(".*[^a-zA-Z0-9].*");
    }

    public static boolean isCellNumberValid(String cellNumber) {
        return cellNumber.matches("^\\+\\d{1,3}\\d{1,10}$");
    }
}
