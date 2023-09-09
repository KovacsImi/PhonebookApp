package com.example.demo8;

import javafx.beans.property.SimpleStringProperty;

public class Human {
    private final SimpleStringProperty firstName;
    private final  SimpleStringProperty lastName;
    private final SimpleStringProperty email;
    private final SimpleStringProperty ID;


    public Human() {
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.ID = new SimpleStringProperty("");
    }

    public Human(String firstName, String lastName, String email) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.ID = new SimpleStringProperty("0");
    }

    public Human(Integer id,String firstName, String lastName, String email) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.ID = new SimpleStringProperty(String.valueOf(id));
    }

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

    public String getID() {
        return ID.get();
    }

    public String IDProperty() {
        return ID.get();
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }


}
