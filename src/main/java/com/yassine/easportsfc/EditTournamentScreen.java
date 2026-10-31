package com.yassine.easportsfc;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class EditTournamentScreen {
    public void show(Stage stage, Scene tournamentScreen, Toernooi toernooi) {

        VBox layout = new VBox();
        layout.setSpacing(15);

        // Titel
        Label title = new Label("Toernooi bewerken:");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        title.setPadding(new Insets(15, 12, 15, 12));

        // Invoervelden vullen met bestaande gegevens
        TextField nameField = new TextField(toernooi.getNaam());
        nameField.setPromptText("Naam");

        DatePicker datePicker = new DatePicker(toernooi.getDatum());
        datePicker.setPromptText("Datum");

        // Navigatie
        HBox nav = new HBox();
        nav.setPadding(new Insets(15, 12, 15, 12));
        nav.setSpacing(10);
        nav.setStyle("-fx-background-color: #ff6500;");

        Button backButton = new Button("Terug");

        backButton.setOnAction(event -> {
            new TournamentScreen().show(stage, tournamentScreen);
        });

        nav.getChildren().add(backButton);

        // Opslaan
        Button saveButton = new Button("Opslaan");

        saveButton.setOnAction(event -> {

            String naam = nameField.getText();
            LocalDate datum = datePicker.getValue();

            // Controleren of alle velden ingevuld zijn
            if (naam.isEmpty() || datum == null)
            {
                System.out.println("Vul alle velden in.");
                return;
            }

            String sql = "UPDATE toernooien SET naam = ?, datum = ? WHERE id = ?";

            try (Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ea_sports_fc",
                    "root",
                    "");
                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(1, naam);
                statement.setDate(2, java.sql.Date.valueOf(datum));
                statement.setInt(3, toernooi.getId());

                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {

                    // Het Toernooi-object bijwerken
                    toernooi.setNaam(naam);
                    toernooi.setDatum(datum);

                    System.out.println("Toernooi succesvol bijgewerkt!");

                    // Terug naar het toernooienoverzicht
                    new TournamentScreen().show(stage, tournamentScreen);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Alles toevoegen aan de layout
        layout.getChildren().addAll(nav, title, nameField, datePicker, saveButton);

        Scene editTournamentScreen = new Scene(layout, 800, 500);

        stage.setTitle("Toernooi bewerken");
        stage.setScene(editTournamentScreen);
    }
}
