package com.example.demo8;

import javafx.beans.property.SimpleStringProperty;

public class Human {
    private final SimpleStringProperty firstName;
    private final  SimpleStringProperty lastName;
    private final SimpleStringProperty email;


    public Human() {
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
    }

    public Human(String firstName, String lastName, String email) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
    }
/*
    public Human(SimpleStringProperty firstName, SimpleStringProperty lastName, SimpleStringProperty email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

 */

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

//    @Override
//    public String toString() {
//        return lastName + " " + firstName + " "  + email;
//    }
}
