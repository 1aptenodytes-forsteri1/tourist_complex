module com.example.jdbcpoj {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.jdbcpoj to javafx.fxml;
    exports com.example.jdbcpoj;
}