package com.example.bookreader;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDFViewerController {
    @FXML
    private BorderPane pdfViewerPane;
    @FXML
    private TextField searchField;
    @FXML
    private MediaView mediaView;
    @FXML
    private ScrollPane scrollPane;

    private PDDocument document;
    private PDFRenderer pdfRenderer;
    private int currentPage = 0;
    private float zoomFactor = 1.0f;

    @FXML
    public void initialize() {
        loadMedia();
    }

    private void loadMedia() {
        Media media = new Media(getClass().getResource("/com/example/bookreader/gif.mp4").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public void loadPDF(String filePath) {
        try {
            // Close the existing document if any
            if (document != null) {
                document.close();
            }
            // Load the new document
            document = PDDocument.load(new File(filePath));
            pdfRenderer = new PDFRenderer(document);
            currentPage = 0; // Reset to the first page
            showPage(currentPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPage(int pageNumber) {
        try {
            if (document != null && pageNumber >= 0 && pageNumber < document.getNumberOfPages()) {
                BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(pageNumber, 90 * zoomFactor);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);

                ImageView imageView = new ImageView(image);
                imageView.setPreserveRatio(true);

                pdfViewerPane.setCenter(imageView);
                scrollPane.setContent(pdfViewerPane);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showPreviousPage() {
        if (document != null && currentPage > 0) {
            currentPage--;
            showPage(currentPage);
        }
    }

    @FXML
    private void showNextPage() {
        if (document != null && currentPage < document.getNumberOfPages() - 1) {
            currentPage++;
            showPage(currentPage);
        }
    }

    @FXML
    private void zoomIn() {
        zoomFactor += 0.1f;
        showPage(currentPage);
    }

    @FXML
    private void zoomOut() {
        if (zoomFactor > 0.1f) {
            zoomFactor -= 0.1f;
            showPage(currentPage);
        }
    }

    @FXML
    private void searchText() {
        String query = searchField.getText();
        if (query != null && !query.isEmpty()) {
            try {
                PDFTextStripper textStripper = new PDFTextStripper();
                for (int i = 0; i < document.getNumberOfPages(); i++) {
                    textStripper.setStartPage(i + 1);
                    textStripper.setEndPage(i + 1);
                    String pageText = textStripper.getText(document);
                    if (pageText.toLowerCase().contains(query.toLowerCase())) {
                        currentPage = i;
                        showPage(currentPage);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
