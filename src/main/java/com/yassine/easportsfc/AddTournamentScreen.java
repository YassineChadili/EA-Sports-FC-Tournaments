package com.yassine.easportsfc;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
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

import java.time.LocalDate;

public class AddTournamentScreen {
    public void show(Stage stage, Scene tournamentScreen ) {
        VBox layout = new VBox();
        layout.setSpacing(15);

        // Titel toevoegen
        Label title = new Label("Toernooi aanmaken:");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        title.setPadding(new Insets(15, 12, 15, 12));

        // Invoerveld
        TextField nameField = new TextField();
        nameField.setPromptText("Naam");

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Selecteer datum");

        // Horizontale rij met knoppen
        HBox nav = new HBox();
        nav.setPadding(new Insets(15, 12, 15, 12));
        nav.setSpacing(10);
        nav.setStyle("-fx-background-color: #ff6500;");
        Button backButton = new Button("Terug");
        backButton.setPrefSize(130, 35);
        backButton.setStyle("-fx-background-color: white; -fx-text-fill: #333333;");

        // Terugknop
        backButton.setOnAction(event -> {
            stage.setScene(tournamentScreen);
            stage.setTitle("Toernooien pagina");
        });

        nav.getChildren().addAll(backButton);

        // Opslaan-knop
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

            String sql = "INSERT INTO toernooien (naam, datum) VALUES (?, ?)";

            try (Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ea_sports_fc",
                    "root",
                    "");
                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(1, naam);
                statement.setDate(2, java.sql.Date.valueOf(datum));

                int rowsCreated = statement.executeUpdate();

                if (rowsCreated > 0) {
                    System.out.println("Toernooi succesvol toegevoegd!");

                    // Terug naar het toernooienoverzicht
                    new TournamentScreen().show(stage, tournamentScreen);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Alles toevoegen aan de layout
        layout.getChildren().addAll(nav, title, nameField, datePicker, saveButton);

        Scene addTournamentScreen  = new Scene(layout, 800, 500);
        stage.setTitle("Toernooi aanmaak pagina");
        stage.setScene(addTournamentScreen );
    }
}
