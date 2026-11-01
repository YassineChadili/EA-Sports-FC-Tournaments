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

public class AddParticipationScreen {
    public void show(Stage stage, Scene participationScreen ) {
        VBox layout = new VBox();
        layout.setSpacing(15);

        // Titel toevoegen
        Label title = new Label("Deelnamens aanmaken:");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        title.setPadding(new Insets(15, 12, 15, 12));

        // Invoervelden
        TextField playerField = new TextField();
        playerField.setPromptText("Speler ID");

        TextField tournamentField = new TextField();
        tournamentField.setPromptText("Toernooi ID");

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
            stage.setScene(participationScreen);
            stage.setTitle("Deelnamend pagina");
        });

        nav.getChildren().addAll(backButton);

        // Opslaan-knop
        Button saveButton = new Button("Opslaan");
        saveButton.setPrefSize(100, 30);

        saveButton.setStyle(
                "-fx-background-color: #ff6500;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand;"
        );

        saveButton.setOnAction(event -> {

            String spelerId = playerField.getText();
            String toernooiId = tournamentField.getText();

            // Controleren of alle velden ingevuld zijn
            if (spelerId.isEmpty() || toernooiId.isEmpty())
            {
                System.out.println("Vul alle velden in.");
                return;
            }

            int speler;
            int toernooi;

            try {
                speler = Integer.parseInt(spelerId);
                toernooi = Integer.parseInt(toernooiId);
            } catch (NumberFormatException e) {
                System.out.println("ID's moeten getallen zijn.");
                return;
            }

            String sql = "INSERT INTO deelnames (speler_id, toernooi_id) VALUES (?, ?)";

            try (Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ea_sports_fc",
                    "root",
                    "");
                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1, speler);
                statement.setInt(2, toernooi);

                int rowsCreated = statement.executeUpdate();

                if (rowsCreated > 0) {
                    System.out.println("Deelname succesvol toegevoegd!");

                    // Terug naar het deelnamesoverzicht
                    new ParticipationScreen().show(stage, participationScreen);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Alles toevoegen aan de layout
        layout.getChildren().addAll(nav, title, playerField, tournamentField, saveButton);

        Scene addParticipationScreen  = new Scene(layout, 800, 500);
        stage.setTitle("Deelname aanmaak pagina");
        stage.setScene(addParticipationScreen );
    }
}
