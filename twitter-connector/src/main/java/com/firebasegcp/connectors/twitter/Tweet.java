package com.firebasegcp.connectors.twitter;

/**
 * Created by dviveiros
 */
public class Tweet {

    private String author;
    private String message;

    public Tweet( String author, String message ) {
        this.author = author;
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
