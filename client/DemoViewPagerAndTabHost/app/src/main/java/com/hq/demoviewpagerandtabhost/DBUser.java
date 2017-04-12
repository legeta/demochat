package com.hq.demoviewpagerandtabhost;

/**
 * Created by My-PC on 4/7/2017.
 */

public class DBUser {
    private String username;
    private String message;
    private int countmess;

    public DBUser(String username, String message, int countmess) {
        this.username = username;
        this.message = message;
        this.countmess = countmess;
    }
    public DBUser(String username) {
        this.username = username;
        this.message = "";
        this.countmess = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCountmess() {
        return countmess;
    }

    public void setCountmess(int countmess) {
        this.countmess = countmess;
    }

    @Override
    public String toString() {
        return "message:'" + message + '\'';
    }
}
