package models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Note implements Serializable {
    private int id;
    private String title;
    private String description;
    private String createdAt;
    public Note(int id, String title, String description, String created_at) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = created_at;
    }

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    @Override
    public String toString() {
        return title;
    }
}
