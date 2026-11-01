package com.yassine.easportsfc;

import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class EditMatchScreen {
    public void show(Stage stage, Scene matchScreen, Partij partij) {

        VBox layout = new VBox();
        layout.setSpacing(15);

        // Titel
        Label title = new Label("Partij bewerken:");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        title.setPadding(new Insets(15, 12, 15, 12));

        // Invoervelden vullen met bestaande gegevens
        TextField player1Field = new TextField(Integer.toString(partij.getSpeler1().getId()));
        player1Field.setPromptText("Speler 1 ID");

        TextField player2Field = new TextField(Integer.toString(partij.getSpeler2().getId()));
        player2Field.setPromptText("Speler 2 ID");

        TextField tournamentField = new TextField(Integer.toString(partij.getToernooi().getId()));
        tournamentField.setPromptText("Toernooi ID");

        TextField scoreField = new TextField(partij.getUitslag());
        scoreField.setPromptText("Uitslag");

        // Navigatie
        HBox nav = new HBox();
        nav.setPadding(new Insets(15, 12, 15, 12));
        nav.setSpacing(10);
        nav.setStyle("-fx-background-color: #ff6500;");

        Button backButton = new Button("Terug");
        backButton.setPrefSize(130, 35);
        backButton.setStyle("-fx-background-color: white; -fx-text-fill: #333333;");

        backButton.setOnAction(event -> {
            new MatchScreen().show(stage, matchScreen);
        });

        nav.getChildren().add(backButton);

        // Opslaan
        Button saveButton = new Button("Opslaan");

        saveButton.setOnAction(event -> {

            String player1Text = player1Field.getText();
            String player2Text = player2Field.getText();
            String tournamentText = tournamentField.getText();
            String uitslag = scoreField.getText();

            // Controleren of alle velden ingevuld zijn
            if (player1Text.isEmpty() || player2Text.isEmpty() || tournamentText.isEmpty() || uitslag.isEmpty())
            {
                System.out.println("Vul alle velden in.");
                return;
            }

            int speler1;
            int speler2;
            int toernooi;

            try {
                speler1 = Integer.parseInt(player1Text);
                speler2 = Integer.parseInt(player2Text);
                toernooi = Integer.parseInt(tournamentText);

            } catch (NumberFormatException e) {
                System.out.println("De ID's moeten getallen zijn.");
                return;
            }

            if (speler1 == speler2) {
                System.out.println("Een speler kan niet tegen zichzelf spelen.");
                return;
            }

            String sql = "UPDATE partijen SET speler1_id = ?, "
                    + "speler2_id = ?, toernooi_id = ?, uitslag = ? "
                    + "WHERE id = ?";

            try (Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ea_sports_fc",
                    "root",
                    "");
                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1, speler1);
                statement.setInt(2, speler2);
                statement.setInt(3, toernooi);
                statement.setString(4, uitslag);
                statement.setInt(5, partij.getId());

                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {

                    System.out.println("Partij succesvol bijgewerkt!");

                    // Terug naar het partijenoverzicht
                    new MatchScreen().show(stage, matchScreen);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Alles toevoegen aan de layout
        layout.getChildren().addAll(nav, title, player1Field, player2Field, tournamentField, scoreField, saveButton);

        Scene editMatchScreen = new Scene(layout, 800, 500);

        stage.setTitle("Partij bewerken");
        stage.setScene(editMatchScreen);
    }
}
