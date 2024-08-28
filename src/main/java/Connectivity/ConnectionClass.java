package Connectivity;

import java.sql.*;

public class ConnectionClass {
    public Connection connection;

    public Connection getConnection(){
        String url = "jdbc:mysql://localhost:3306/tourist_complex";
        String userName = "root";
        String password = "password";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try{

            connection = DriverManager.getConnection(url,userName,password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}
