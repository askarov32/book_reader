package com.example.bookreader;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookController {
    @FXML
    private ListView<String> bookList;
    @FXML
    private BorderPane pdfViewer;

    private PDFViewerController pdfViewerController;

    @FXML
    public void initialize() {
        loadBookList();
        bookList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadPDF(newSelection);
            }
        });
        loadPDFViewer();
    }

    private void loadBookList() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, name FROM books");

            while (resultSet.next()) {
                bookList.getItems().add(resultSet.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadPDFViewer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookreader/pdf-viewer.fxml"));
            BorderPane pdfViewerPane = loader.load();
            pdfViewerController = loader.getController();
            pdfViewer.setCenter(pdfViewerPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPDF(String bookName) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT file_path FROM books WHERE name = '" + bookName + "'");

            if (resultSet.next()) {
                String filePath = resultSet.getString("file_path");
                pdfViewerController.loadPDF(filePath);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
