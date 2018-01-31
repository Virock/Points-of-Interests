/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author viroc
 */
public class User {

    String email, username;

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }

}
