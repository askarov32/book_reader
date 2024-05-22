package com.example.bookreader;

public class Book {
    private int id;
    private String name;
    private String author;
    private String file_path;

    public Book() {
    }

    public Book(int id, String name, String author, String file_path) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.file_path = file_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
