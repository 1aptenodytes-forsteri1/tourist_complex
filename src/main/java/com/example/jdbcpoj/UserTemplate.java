package com.example.jdbcpoj;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserTemplate {
    UserTemplate(Integer id, String surname){
        this.id.set(id);
        this.surname.set(surname);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    private SimpleIntegerProperty id = new SimpleIntegerProperty(this,"id",0);
    private SimpleStringProperty surname = new SimpleStringProperty(this,"surname",null);
}
