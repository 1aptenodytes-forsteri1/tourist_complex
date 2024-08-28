package com.example.jdbcpoj;

import Connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.Date;


public class AddServiceController implements Initializable {
    static public int billId = 0;
    double amountOfMoney;
    ArrayList<EventServiceTemplate> data = new ArrayList<EventServiceTemplate>();
    @FXML
    ComboBox eventList;
    @FXML
    TextField amount_field;
    @FXML
    TableView table;
    @FXML
    TableColumn<EventServiceTemplate,String> name;
    @FXML
    TableColumn<EventServiceTemplate, Date> date;
    @FXML
    TableColumn<EventServiceTemplate,Integer> number;
    @FXML
    TableColumn<EventServiceTemplate,Double> amount;
    @FXML
    private AnchorPane window;

    public AddServiceController() {
    }

    static HashMap<Integer, String> map = new HashMap<Integer, String>();

    private List<String> getService() throws SQLException {
        ConnectionClass connectCl = new ConnectionClass();
        Connection connection = connectCl.getConnection();
        String query = "SELECT service_id, title FROM service";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            map.put(rs.getInt(1), rs.getString(2));
        }
        return new ArrayList<String>(map.values());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            eventList.setItems(FXCollections.observableList(getService()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            fillTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer getKeyByValue(String value) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return 0;
    }
    void fillTable() throws SQLException {
        data.clear();
        table.getItems().clear();
        ConnectionClass connectCl = new ConnectionClass();
        Connection connection = connectCl.getConnection();
        String query = "SELECT title, date, service_order.amount, IF(bill.check_in_date >= curdate(),datediff(bill.eviction_date, bill.check_in_date),datediff(bill.eviction_date, curdate())) * service.cost * service_order.amount AS amount FROM service INNER JOIN service_order ON service.service_id = service_order.service_id INNER JOIN bill ON bill.bill_id = service_order.bill_id WHERE service_order.bill_id = " + billId + ";";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while(rs.next()){
            data.add(new EventServiceTemplate(rs.getString(1), rs.getDate(2), rs.getInt(3),rs.getDouble(4)));
        }
        name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        date.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        number.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asObject());
        amount.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        table.getItems().addAll(data);
    }
    @FXML
    protected void onSaveButtonClick() throws SQLException {
        if (!eventList.getSelectionModel().isEmpty() & !amount_field.getText().isEmpty()) {
            ConnectionClass connectCl = new ConnectionClass();
            Integer amount = 1;
            try {
                amount = Integer.parseInt(amount_field.getText());
            } catch (NumberFormatException e) {
                amount_field.setText("1");
            }
            String query = "INSERT INTO service_order (service_id, bill_id, amount, date) VALUES (?,?,?, GREATEST(curdate(),(SELECT check_in_date FROM bill WHERE bill_id = " + billId +")))";
            try (Connection connection = connectCl.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Установите значения параметров в запросе
                preparedStatement.setInt(1, getKeyByValue(eventList.getValue().toString()));
                preparedStatement.setInt(2, billId);
                preparedStatement.setInt(3, amount);
                // Выполните запрос
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ConnectionClass connectId = new ConnectionClass();
            Connection connection = connectCl.getConnection();
            String query1 = "SELECT IF(bill.check_in_date >= curdate(),datediff(bill.eviction_date, bill.check_in_date),datediff(bill.eviction_date, curdate())) * service.cost * service_order.amount AS amount FROM service INNER JOIN service_order ON service.service_id = service_order.service_id INNER JOIN bill ON bill.bill_id = service_order.bill_id WHERE service_order.bill_id = " + billId + ";";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query1);
            if(rs.next()){
                amountOfMoney = rs.getDouble(1);
            }
            String plusQuery = "UPDATE bill SET amount = amount + " + amountOfMoney + " WHERE bill_id = " + billId + ";";
            try (Connection connection2 = connectCl.getConnection();
                 PreparedStatement preparedStatement = connection2.prepareStatement(plusQuery)) {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            fillTable();
        }

    }
    @FXML
    protected void onBackButtonClick() throws IOException {
        AnchorPane ap = FXMLLoader.load(getClass().getResource("current_bills.fxml"));
        window .getChildren().setAll(ap);
    }
}

