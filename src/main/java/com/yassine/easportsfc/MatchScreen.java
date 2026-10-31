package com.yassine.easportsfc;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchScreen {
    public void show(Stage stage, Scene homeScreen ) {
        VBox layout = new VBox();

        // Titel toevoegen
        Label title = new Label("Partijen overzicht:");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        title.setPadding(new Insets(15, 12, 15, 12));

        Label description = new Label("Hieronder staan de partijen");
        description.setPadding(new Insets(10, 12, 15, 12));

        // Horizontale rij met knoppen
        HBox nav = new HBox();
        nav.setPadding(new Insets(15, 12, 15, 12));
        nav.setSpacing(10);
        nav.setStyle("-fx-background-color: #ff6500;");
        Button backButton = new Button("Terug");
        backButton.setPrefSize(100, 20);
        Button matchAdd = new Button("Partij aanmaken");
        matchAdd.setPrefSize(100, 20);

        Scene matchScreen  = new Scene(layout, 800, 500);

        // Terugknop
        backButton.setOnAction(event -> {
            stage.setScene(homeScreen);
            stage.setTitle("Home");
        });

        matchAdd.setOnAction(event -> {
            AddMatchScreen addMatchScreen = new AddMatchScreen();
            addMatchScreen.show(stage, matchScreen);
        });

        nav.getChildren().addAll(backButton, matchAdd);

        // Alles toevoegen aan de layout
        layout.getChildren().addAll(nav, title, description);

        // Partijen ophalen uit de database
        String sql = "SELECT p.id, p.speler1_id, p.speler2_id, p.toernooi_id, p.uitslag, " +
                "s1.naam AS speler1, " +
                "s2.naam AS speler2, " +
                "t.naam AS toernooi " +
                "FROM partijen p " +
                "JOIN spelers s1 ON p.speler1_id = s1.id " +
                "JOIN spelers s2 ON p.speler2_id = s2.id " +
                "JOIN toernooien t ON p.toernooi_id = t.id";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ea_sports_fc",
                "root",
                "");
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet output = statement.executeQuery()) {

            while (output.next()) {

                // Gegevens ophalen
                int id = output.getInt("id");

                int speler1Id = output.getInt("speler1_id");
                int speler2Id = output.getInt("speler2_id");
                int toernooiId = output.getInt("toernooi_id");

                String speler1 = output.getString("speler1");
                String speler2 = output.getString("speler2");
                String toernooi = output.getString("toernooi");
                String uitslag = output.getString("uitslag");

                // Speler-objecten aanmaken
                Speler speler1Object = new Speler(speler1Id, speler1, 0);
                Speler speler2Object = new Speler(speler2Id, speler2, 0);

                // Toernooi-object aanmaken
                Toernooi toernooiObject = new Toernooi(toernooiId, toernooi, null);

                // Partij-object aanmaken
                Partij partij = new Partij(
                        id,
                        speler1Object,
                        speler2Object,
                        toernooiObject,
                        uitslag
                );

                // Labels maken
                Label player1Label = new Label("Speler 1: " + speler1);
                Label player2Label = new Label("Speler 2: " + speler2);
                Label tournamentLabel = new Label("Toernooi: " + toernooi);
                Label resultLabel = new Label("Uitslag: " + uitslag);

                // Ruimte rond de labels
                player1Label.setPadding(new Insets(5, 12, 0, 12));
                player2Label.setPadding(new Insets(5, 12, 0, 12));
                tournamentLabel.setPadding(new Insets(5, 12, 0, 12));
                resultLabel.setPadding(new Insets(5, 12, 0, 12));

                // Horizontale rij maken
                HBox matchRow = new HBox();
                matchRow.setSpacing(15);

                Button editButton = new Button("Bewerken");

                editButton.setOnAction(event -> {
                    EditMatchScreen editMatchScreen = new EditMatchScreen();
                    editMatchScreen.show(stage, matchScreen, partij);
                });

                // Verwijderen-knop
                Button deleteButton = new Button("Verwijderen");

                deleteButton.setOnAction(event -> {

                    String deleteSql = "DELETE FROM partijen WHERE id = ?";

                    try (Connection deleteConnection = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/ea_sports_fc",
                            "root",
                            "");
                         PreparedStatement deleteStatement =
                                 deleteConnection.prepareStatement(deleteSql)) {

                        deleteStatement.setInt(1, id);

                        int rowsDeleted = deleteStatement.executeUpdate();

                        if (rowsDeleted > 0) {
                            System.out.println("Partij succesvol verwijderd!");

                            // Partijenoverzicht opnieuw laden
                            new MatchScreen().show(stage, homeScreen);
                        } else {
                            System.out.println("Partij niet gevonden.");
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

                // Rij vullen
                matchRow.getChildren().addAll(player1Label, player2Label, tournamentLabel, resultLabel, editButton, deleteButton);

                // Rij toevoegen aan layout
                layout.getChildren().add(matchRow);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        stage.setTitle("Partijen pagina");
        stage.setScene(matchScreen );
    }
}
