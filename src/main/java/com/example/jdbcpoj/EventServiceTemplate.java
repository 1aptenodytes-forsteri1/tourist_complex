package com.example.jdbcpoj;

import javafx.beans.property.*;

import java.util.Date;

public class EventServiceTemplate {

    public EventServiceTemplate(String name, Date date, Integer number, Double amount){
        this.name.set(name);
        this.date.set(date);
        this.number.set(number);
        this.amount.set(amount);
    }
    private SimpleStringProperty name = new SimpleStringProperty(this,"name","");
    private SimpleObjectProperty<Date> date = new SimpleObjectProperty<Date>(this,"date",null);
    private SimpleIntegerProperty number = new SimpleIntegerProperty(this,"number",0);
    private SimpleDoubleProperty amount = new SimpleDoubleProperty(this,"amount",0);

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Date getDate() {
        return date.get();
    }

    public SimpleObjectProperty<Date> dateProperty() {
        return date;
    }

    public void setDate(Date date) {
        this.date.set(date);
    }

    public int getNumber() {
        return number.get();
    }

    public SimpleIntegerProperty numberProperty() {
        return number;
    }

    public void setNumber(int number) {
        this.number.set(number);
    }

    public double getAmount() {
        return amount.get();
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }



}
