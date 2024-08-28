package com.example.jdbcpoj;

import Connectivity.ConnectionClass;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class CurrentBillsController implements Initializable {
    ArrayList<BillTemplate> data = new ArrayList<BillTemplate>();
    @FXML
    TableView<BillTemplate> table;
    @FXML
    TableColumn<BillTemplate,Integer> id;
    @FXML
    TableColumn<BillTemplate,Date> check_in;
    @FXML
    TableColumn<BillTemplate,Date> eviction;
    @FXML
    TableColumn<BillTemplate,Double> amount;
    @FXML
    TableColumn<BillTemplate,String> surname;
    @FXML
    TableColumn<BillTemplate,String> name;
    @FXML
    TableColumn<BillTemplate,Integer> roomNumber;
    @FXML
    Button addEventButton;
    @FXML
    Button addServiceButton;
    @FXML
    Button addBillButton;
    @FXML
    private AnchorPane window;

    public CurrentBillsController(){
    }

    @FXML
    protected void onActiveButtonClick() throws SQLException {
        data.clear();
        table.getItems().clear();
        addEventButton.setVisible(true);
        addServiceButton.setVisible(true);
        addBillButton.setVisible(true);
        addBillButton.setDisable(false);
        ConnectionClass connectCl = new ConnectionClass();
        Connection connection = connectCl.getConnection();
        String query = "SELECT bill.bill_id, check_in_date, eviction_date, amount, name, surname, room.room_number FROM bill INNER JOIN client ON bill.client_id = client.client_id INNER JOIN room_order ON room_order.bill_id = bill.bill_id INNER JOIN room ON room.room_number = room_order.room_number WHERE eviction_date >= current_date();";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while(rs.next()){
            data.add(new BillTemplate(rs.getInt(1),rs.getDate(2), rs.getDate(3), rs.getDouble(4), rs.getString(5),rs.getString(6),rs.getInt(7)));
        }
        id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        check_in.setCellValueFactory(cellData -> cellData.getValue().check_inProperty());
        eviction.setCellValueFactory(cellData -> cellData.getValue().evictionProperty());
        amount.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        surname.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        roomNumber.setCellValueFactory(cellData -> cellData.getValue().roomProperty().asObject());
        table.getItems().addAll(data);
        addServiceButton.setDisable(true);
        addEventButton.setDisable(true);
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                AddEventController.billId = newSelection.getId();
                AddServiceController.billId = newSelection.getId();
                addServiceButton.setDisable(false);
                addEventButton.setDisable(false);
            }
        });
    }

    @FXML
    protected void onArchiveButtonClick() throws SQLException {
        data.clear();
        table.getItems().clear();
        addEventButton.setVisible(false);
        addServiceButton.setVisible(false);
        addBillButton.setVisible(false);
        addBillButton.setDisable(true);
        ConnectionClass connectCl = new ConnectionClass();
        Connection connection = connectCl.getConnection();
        String query = "SELECT bill.bill_id, check_in_date, eviction_date, amount, name, surname, room.room_number FROM bill INNER JOIN client ON bill.client_id = client.client_id INNER JOIN room_order ON room_order.bill_id = bill.bill_id INNER JOIN room ON room.room_number = room_order.room_number WHERE eviction_date < current_date();";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while(rs.next()){
            data.add(new BillTemplate(rs.getInt(1),rs.getDate(2), rs.getDate(3), rs.getDouble(4), rs.getString(5),rs.getString(6),rs.getInt(7)));
        }
        id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        check_in.setCellValueFactory(cellData -> cellData.getValue().check_inProperty());
        eviction.setCellValueFactory(cellData -> cellData.getValue().evictionProperty());
        amount.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        surname.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        roomNumber.setCellValueFactory(cellData -> cellData.getValue().roomProperty().asObject());
        table.getItems().addAll(data);

    }
    @FXML
    protected void onServiceButtonClick() throws IOException {
        AnchorPane ap = FXMLLoader.load(getClass().getResource("add_service.fxml"));
        window .getChildren().setAll(ap);
    }
    @FXML
    protected void onEventButtonClick() throws IOException {
        AnchorPane ap = FXMLLoader.load(getClass().getResource("add_event.fxml"));
        window .getChildren().setAll(ap);
    }
    @FXML
    protected void onBillButtonClick() throws IOException {
        AnchorPane ap = FXMLLoader.load(getClass().getResource("add_bill.fxml"));
        window .getChildren().setAll(ap);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            onActiveButtonClick();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
