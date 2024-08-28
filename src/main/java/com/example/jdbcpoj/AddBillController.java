package com.example.jdbcpoj;

import Connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddBillController implements Initializable {
    ArrayList<RoomTemplate> data = new ArrayList<RoomTemplate>();
    ArrayList<UserTemplate> data1 = new ArrayList<UserTemplate>();
    @FXML
    TableView<RoomTemplate> table;

    @FXML
    TableColumn<RoomTemplate,Integer> id;
    @FXML
    TableColumn<RoomTemplate,String> rClass;
    @FXML
    TableColumn<RoomTemplate,String> status;
    @FXML
    TableColumn<RoomTemplate,Integer> beds;
    @FXML
    TableColumn<RoomTemplate, Double> cost;
    @FXML
    Label idAlert;
    @FXML
    Label phoneAlert;
    @FXML
    TextField idField;
    @FXML
    TextField nameField;
    @FXML
    TextField surnameField;
    @FXML
    TextField phoneField;
    @FXML
    DatePicker checkInPicker;
    @FXML
    DatePicker evictionPicker;
    @FXML
    Label utensils;
    @FXML
    Label dateAlert;
    @FXML
    Label roomAlert;
    @FXML
    Label endAlert;
    @FXML
    private AnchorPane window;
    @FXML
    private TableView clientTable;
    @FXML
    private TableColumn<UserTemplate, Integer> clientId;
    @FXML
    private TableColumn<UserTemplate, String> clientSurname;

    double roomCost = 0;
    boolean roomSelected = true;
    int billId;
    int roomNumber = 0;
    String regex = "^(\\+\\d{1,3})\\d{5,12}$";
    Pattern pattern = Pattern.compile(regex);
    LinkedHashSet<String> utensilsSet= new LinkedHashSet<String>();
    String utValue = "";


    private List<String> getUtensils() throws SQLException {
        ArrayList<String> al = new ArrayList<String>();
        ConnectionClass connectCl = new ConnectionClass();
        Connection connection = connectCl.getConnection();
        String query = "SELECT DISTINCT title FROM utensils ";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            al.add(rs.getString(1));
        }
        return al;
    }

    protected void fillClient(Integer id) throws SQLException {

        ConnectionClass connectCl = new ConnectionClass();
        Connection connection = connectCl.getConnection();
        String query = "SELECT * FROM client";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()){
            if (id == rs.getInt(1)){
                nameField.setText(rs.getString(2));
                surnameField.setText(rs.getString(3));
                phoneField.setText(rs.getString(4));
                nameField.setDisable(true);
                surnameField.setDisable(true);
                phoneField.setDisable(true);
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idField.setOnKeyReleased(event -> {
            try {
                handleKeyReleasedId(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        phoneField.setOnKeyReleased(event -> {
            try {
                handleKeyReleasedPhone(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                roomNumber = newSelection.getId();
                roomCost = newSelection.getCost();
            }
        });
        try {
            fillTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillTable() throws SQLException {
        ConnectionClass connectCl = new ConnectionClass();
        Connection connection = connectCl.getConnection();
        String query = "SELECT client_id, surname FROM client;";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while(rs.next()) {
            data1.add(new UserTemplate(rs.getInt(1), rs.getString(2)));
        }
        clientId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        clientSurname.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        clientTable.getItems().addAll(data1);
    }
    private void handleKeyReleasedId(KeyEvent event) throws SQLException {

        String input = ((TextField) event.getSource()).getText();
        idAlert.setVisible(false);
        nameField.setDisable(false);
        surnameField.setDisable(false);
        phoneField.setDisable(false);
        try {
            fillClient(Integer.parseInt(idField.getText()));
        }catch (NumberFormatException e){
            idAlert.setVisible(true);
        }
    }
    private void handleKeyReleasedPhone(KeyEvent event) throws SQLException {
        String input = ((TextField) event.getSource()).getText();

        if (!pattern.matcher(phoneField.getText()).matches()){
            phoneAlert.setVisible(true);
        }else {
            phoneAlert.setVisible(false);
        }

    }
    @FXML
    protected void onFindRoomsClick() throws SQLException {
        data.clear();
        table.getItems().clear();
        dateAlert.setVisible(false);
        ConnectionClass connectCl = new ConnectionClass();
        Connection connection = connectCl.getConnection();
        if (checkInPicker.getValue() == null || evictionPicker.getValue() == null || checkInPicker.getValue().isAfter(evictionPicker.getValue())){
            dateAlert.setVisible(true);
            return;
        }
        String query = "SELECT * FROM room WHERE room_number NOT IN(SELECT room.room_number FROM room INNER JOIN room_order ON room.room_number = room_order.room_number INNER JOIN bill ON room_order.bill_id = bill.bill_id WHERE ('"+checkInPicker.getValue()+"' BETWEEN bill.check_in_date AND bill.eviction_date) AND ('"+evictionPicker.getValue()+"' BETWEEN bill.check_in_date AND bill.eviction_date));";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while(rs.next()) {
            //if (rs.getInt(6) == utensilsSet.size()) {
                data.add(new RoomTemplate(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDouble(5)));
            //}
        }
        id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        rClass.setCellValueFactory(cellData -> cellData.getValue().rClassProperty());
        status.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        beds.setCellValueFactory(cellData -> cellData.getValue().bedsProperty().asObject());
        cost.setCellValueFactory(cellData -> cellData.getValue().costProperty().asObject());
        table.getItems().addAll(data);
    }
    @FXML
    protected void onAddBillClick() throws SQLException {
        if (roomNumber == 0 & roomCost == 0){
            roomAlert.setVisible(true);
            return;
        }
        ConnectionClass connectCl = new ConnectionClass();
        if(!nameField.isDisabled() & !surnameField.isDisabled() & !phoneField.isDisabled()){
            String query1 = "INSERT INTO client (client_id, name , surname , phone) VALUES (?,?,?,?)";
            try (Connection connection = connectCl.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query1)) {
                // Установите значения параметров в запросе
                preparedStatement.setInt(1, Integer.parseInt(idField.getText()));
                preparedStatement.setString(2, nameField.getText());
                preparedStatement.setString(3, surnameField.getText());
                preparedStatement.setString(4, phoneField.getText());
                // Выполните запрос
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        String query2 = "INSERT INTO bill (client_id, admin_id, check_in_date, eviction_date, amount) VALUES (?,?,?,?,?)";
        try (Connection connection = connectCl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query2)) {
            // Установите значения параметров в запросе
            preparedStatement.setInt(1, Integer.parseInt(idField.getText()));
            preparedStatement.setInt(2, AuthorizationController.adminId);
            preparedStatement.setDate(3, Date.valueOf(checkInPicker.getValue()));
            preparedStatement.setDate(4, Date.valueOf(evictionPicker.getValue()));
            preparedStatement.setDouble(5, roomCost * ChronoUnit.DAYS.between(checkInPicker.getValue(), evictionPicker.getValue()));

            // Выполните запрос
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ConnectionClass connectId = new ConnectionClass();
        Connection connection = connectCl.getConnection();
        String query = "SELECT MAX(bill_id) FROM bill";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        if (rs.next()){
            billId = rs.getInt(1);
        }

        String query3 = "INSERT INTO room_order (bill_id, room_number, date) VALUES (" + billId + "," + roomNumber + ",CURDATE())";
        try (Connection connectionOrder = connectCl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query3)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        roomAlert.setVisible(false);
        data1.clear();
        clientTable.getItems().clear();
        fillTable();
        endAlert.setVisible(true);
    }
    @FXML
    protected void onBackButtonClick() throws IOException {
        AnchorPane ap = FXMLLoader.load(getClass().getResource("current_bills.fxml"));
        window .getChildren().setAll(ap);
    }
}
