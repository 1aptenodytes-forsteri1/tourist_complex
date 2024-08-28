package com.example.jdbcpoj;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class RoomTemplate {
    public RoomTemplate(Integer id, String rClass, String status, Integer beds, Double cost){
        this.id.set(id);
        this.rClass.set(rClass);
        this.status.set(status);
        this.beds.set(beds);
        this.cost.set(cost);

    }
    private SimpleIntegerProperty id = new SimpleIntegerProperty(this,"id",0);
    private SimpleStringProperty rClass = new SimpleStringProperty(this,"class",null);
    private SimpleStringProperty status = new SimpleStringProperty(this,"status",null);
    private SimpleIntegerProperty beds = new SimpleIntegerProperty(this,"beds",0);
    private SimpleDoubleProperty cost = new SimpleDoubleProperty(this,"cost",0);
    private int matches;

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
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

    public String getrClass() {
        return rClass.get();
    }

    public SimpleStringProperty rClassProperty() {
        return rClass;
    }

    public void setrClass(String rClass) {
        this.rClass.set(rClass);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public int getBeds() {
        return beds.get();
    }

    public SimpleIntegerProperty bedsProperty() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds.set(beds);
    }

    public double getCost() {
        return cost.get();
    }

    public SimpleDoubleProperty costProperty() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost.set(cost);
    }

}
