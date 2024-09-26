package com.example.smilecare;

public class User {

    private String id;
    private String email;
    private String name;
    private String lastName;
    private String method;

    public User(String id, String email, String name, String lastName, String method) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.method = method;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMethod() {
        return method;
    }
}
