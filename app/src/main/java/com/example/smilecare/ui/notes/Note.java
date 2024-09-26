package com.example.smilecare.ui.notes;

public class Note {

    private String id;
    private String userId;
    private String text;
    private int priority;

    public void setId(String id) {
        this.id = id;
    }

    public Note(String id, String userId, String text, int priority) {
        this.id = id;
        this.userId = userId;
        this.text = text;
        this.priority = priority;
    }

    public Note() {
    }

    public String getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getPriority() {
        return priority;
    }

}
