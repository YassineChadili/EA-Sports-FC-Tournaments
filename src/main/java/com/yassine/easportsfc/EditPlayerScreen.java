package com.yassine.easportsfc;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditPlayerScreen {
    public void show(Stage stage, Scene playerScreen, Speler speler) {

        VBox layout = new VBox();
        layout.setSpacing(15);

        // Titel
        Label title = new Label("Speler bewerken:");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        title.setPadding(new Insets(15, 12, 15, 12));

        // Invoervelden vullen met bestaande gegevens
        TextField nameField = new TextField(speler.getNaam());
        nameField.setPromptText("Naam");

        TextField ratingField = new TextField(Integer.toString(speler.getRating()));
        ratingField.setPromptText("Rating");

        // Navigatie
        HBox nav = new HBox();
        nav.setPadding(new Insets(15, 12, 15, 12));
        nav.setSpacing(10);
        nav.setStyle("-fx-background-color: #ff6500;");

        Button backButton = new Button("Terug");
        backButton.setPrefSize(130, 35);
        backButton.setStyle("-fx-background-color: white; -fx-text-fill: #333333;");

        backButton.setOnAction(event -> {
            new PlayerScreen().show(stage, playerScreen);
        });

        nav.getChildren().add(backButton);

        // Opslaan
        Button saveButton = new Button("Opslaan");
        saveButton.setPrefSize(100, 30);

        saveButton.setStyle(
                "-fx-background-color: #ff6500;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand;"
        );

        saveButton.setOnAction(event -> {

            String naam = nameField.getText();
            String ratingText = ratingField.getText();

            if (naam.isEmpty() || ratingText.isEmpty())
            {
                System.out.println("Vul alle velden in.");
                return;
            }

            int rating;

            try {
                rating = Integer.parseInt(ratingText);
            } catch (NumberFormatException e) {
                System.out.println("De rating moet een getal zijn.");
                return;
            }

            String sql = "UPDATE spelers SET naam = ?, rating = ? WHERE id = ?";

            try (Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ea_sports_fc",
                    "root",
                    "");
                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(1, naam);
                statement.setInt(2, rating);
                statement.setInt(3, speler.getId());

                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {
                    // Het Speler-object bijwerken
                    speler.setNaam(naam);
                    speler.setRating(rating);

                    System.out.println("Speler succesvol bijgewerkt!");

                    // Terug naar het spelersoverzicht
                    new PlayerScreen().show(stage, playerScreen);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        layout.getChildren().addAll(nav, title, nameField, ratingField, saveButton);

        Scene editPlayerScreen = new Scene(layout, 800, 500);

        stage.setTitle("Speler bewerken");
        stage.setScene(editPlayerScreen);
    }
}
