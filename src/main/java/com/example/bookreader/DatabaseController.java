package com.example.bookreader;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseController {

    @FXML
    private Label dataLabel;

    @FXML
    public void initialize() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM your_table");

            StringBuilder data = new StringBuilder();
            while (resultSet.next()) {
                data.append(resultSet.getString("your_column")).append("\n");
            }
            dataLabel.setText(data.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
