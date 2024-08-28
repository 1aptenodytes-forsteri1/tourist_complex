package com.example.jdbcpoj;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

import java.util.Date;

public class BillTemplate {
    public BillTemplate(int id, Date check_in, Date eviction, double amount, String surname, String name, int room){
        this.id.set(id);
        this.check_in.set(check_in);
        this.eviction.set(eviction);
        this.amount.set(amount);
        this.surname.set(surname);
        this.name.set(name);
        this.room.set(room);
    }


    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public Date getCheck_in() {
        return check_in.get();
    }

    public ObjectProperty<Date> check_inProperty() {
        return check_in;
    }

    public void setCheck_in(Date check_in) {
        this.check_in.set(check_in);
    }

    public Date getEviction() {
        return eviction.get();
    }

    public ObjectProperty<Date> evictionProperty() {
        return eviction;
    }

    public void setEviction(Date eviction) {
        this.eviction.set(eviction);
    }

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
    public int getRoom() {
        return room.get();
    }

    public SimpleIntegerProperty roomProperty() {
        return room;
    }

    public void setRoom(int room) {
        this.room.set(room);
    }

    private SimpleIntegerProperty id = new SimpleIntegerProperty(this,"id",0);
    private SimpleObjectProperty<Date> check_in = new SimpleObjectProperty<Date>(this,"check_in",null);
    private SimpleObjectProperty<Date> eviction = new SimpleObjectProperty<Date>(this,"eviction",null);
    private SimpleDoubleProperty amount = new SimpleDoubleProperty(this,"amount",0);
    private SimpleStringProperty surname = new SimpleStringProperty(this,"surname","");
    private SimpleStringProperty name = new SimpleStringProperty(this,"name","");
    private SimpleIntegerProperty room = new SimpleIntegerProperty(this,"room",0);


}
