package com.example.jdbcpoj;

import Connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AuthorizationController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private AnchorPane window;
    public static int adminId;

    public AuthorizationController() {
    }

    @FXML
    protected void onHelloButtonClick() throws SQLException, IOException {
        ConnectionClass connectCl = new ConnectionClass();
        Connection connection = connectCl.getConnection();
        String query = "SELECT login,password, admin_id FROM admin";
        String log = login.getText();
        String pass = password.getText();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);

        String sv = "Доступ заблокирован";
        while(rs.next()){
            if(!rs.getString(1).equals(log))continue;
            if(rs.getString(2).equals(pass)){
                adminId = rs.getInt(3);
                AnchorPane ap = FXMLLoader.load(getClass().getResource("current_bills.fxml"));
                window .getChildren().setAll(ap);
            }
        }
        welcomeText.setText(sv);

    }
}