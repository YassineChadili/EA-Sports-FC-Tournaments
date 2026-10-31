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

public class ParticipationScreen {
    public void show(Stage stage, Scene homeScreen ) {
        VBox layout = new VBox();

        // Titel toevoegen
        Label title = new Label("Deelnamens overzicht:");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        title.setPadding(new Insets(15, 12, 15, 12));

        Label description = new Label("Hieronder staan spelers die meedoen aan een toernooi");
        description.setPadding(new Insets(10, 12, 15, 12));

        // Horizontale rij met knoppen
        HBox nav = new HBox();
        nav.setPadding(new Insets(15, 12, 15, 12));
        nav.setSpacing(10);
        nav.setStyle("-fx-background-color: #ff6500;");
        Button backButton = new Button("Terug");
        backButton.setPrefSize(100, 20);
        Button participationAdd = new Button("Deelname aanmaken");
        participationAdd.setPrefSize(100, 20);

        Scene participationScreen = new Scene(layout, 800, 500);

        // Terugknop
        backButton.setOnAction(event -> {
            stage.setScene(homeScreen);
            stage.setTitle("Home");
        });

        participationAdd.setOnAction(event -> {
            AddParticipationScreen addParticipationScreen = new AddParticipationScreen();
            addParticipationScreen.show(stage, participationScreen);
        });

        nav.getChildren().addAll(backButton, participationAdd);

        // Alles toevoegen aan de layout
        layout.getChildren().addAll(nav, title, description);

        // Deelnames ophalen uit de database
        String sql = "SELECT d.speler_id, d.toernooi_id, " +
                "s.naam AS speler_naam, t.naam AS toernooi_naam " +
                "FROM deelnames d " +
                "JOIN spelers s ON d.speler_id = s.id " +
                "JOIN toernooien t ON d.toernooi_id = t.id";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ea_sports_fc",
                "root",
                "");
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet output = statement.executeQuery()) {

            while (output.next()) {

                int spelerId = output.getInt("speler_id");
                int toernooiId = output.getInt("toernooi_id");

                String spelerNaam = output.getString("speler_naam");
                String toernooiNaam = output.getString("toernooi_naam");

                // Labels
                Label speler = new Label("Speler: " + spelerNaam);
                Label toernooi = new Label("Toernooi: " + toernooiNaam);

                speler.setPadding(new Insets(5, 12, 0, 12));
                toernooi.setPadding(new Insets(5, 12, 0, 12));

                // Horizontale rij
                HBox participationRow = new HBox();
                participationRow.setSpacing(15);

                // Verwijderen
                Button deleteButton = new Button("Verwijderen");

                deleteButton.setOnAction(event -> {

                    String deleteSql = "DELETE FROM deelnames " +
                            "WHERE speler_id = ? AND toernooi_id = ?";

                    try (Connection deleteConnection =
                                 DriverManager.getConnection(
                                         "jdbc:mysql://localhost:3306/ea_sports_fc",
                                         "root",
                                         "");
                         PreparedStatement deleteStatement =
                                 deleteConnection.prepareStatement(deleteSql)) {

                        deleteStatement.setInt(1, spelerId);
                        deleteStatement.setInt(2, toernooiId);

                        int rowsDeleted = deleteStatement.executeUpdate();

                        if (rowsDeleted > 0) {
                            System.out.println(
                                    "Deelname succesvol verwijderd!"
                            );

                            new ParticipationScreen().show(stage, homeScreen);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

                // De volledige rij toevoegen
                participationRow.getChildren().addAll(speler, toernooi, deleteButton);

                layout.getChildren().add(participationRow);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        stage.setTitle("Deelnamens pagina");
        stage.setScene(participationScreen );
    }
}
