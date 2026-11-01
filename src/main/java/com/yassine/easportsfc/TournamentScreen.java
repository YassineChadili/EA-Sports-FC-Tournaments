package com.yassine.easportsfc;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.*;

public class TournamentScreen {
    public void show(Stage stage, Scene homeScreen ) {
        VBox layout = new VBox();

        // Titel toevoegen
        Label title = new Label("Toernooien overzicht:");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        title.setPadding(new Insets(15, 12, 15, 12));

        Label description = new Label("Hieronder staan aangemaakte toernooien");
        description.setPadding(new Insets(10, 12, 15, 12));

        // Horizontale rij met knoppen
        HBox nav = new HBox();
        nav.setPadding(new Insets(15, 12, 15, 12));
        nav.setSpacing(10);
        nav.setStyle("-fx-background-color: #ff6500;");
        Button backButton = new Button("Terug");
        backButton.setPrefSize(130, 35);
        backButton.setStyle("-fx-background-color: white; -fx-text-fill: #333333;");
        Button tournamentAdd = new Button("Toernooi aanmaken");
        tournamentAdd.setPrefSize(130, 35);
        tournamentAdd.setStyle("-fx-background-color: white; -fx-text-fill: #333333;");

        Scene tournamentScreen  = new Scene(layout, 800, 500);

        // Terugknop
        backButton.setOnAction(event -> {
            stage.setScene(homeScreen);
            stage.setTitle("Home");
        });

        tournamentAdd.setOnAction(event -> {
            AddTournamentScreen addTournamentScreen = new AddTournamentScreen();
            addTournamentScreen.show(stage, tournamentScreen);
        });

        nav.getChildren().addAll(backButton, tournamentAdd);

        // Alles toevoegen aan de layout
        layout.getChildren().addAll(nav, title, description);

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ea_sports_fc", "root", "");

            Statement statement = connection.createStatement();
            ResultSet output = statement.executeQuery("SELECT * FROM toernooien");

            while (output.next()) {

                // Gegevens uit de database in een Toernooi-object plaatsen
                Toernooi toernooi = new Toernooi(
                        output.getInt("id"),
                        output.getString("naam"),
                        output.getDate("datum").toLocalDate()
                );

                // Gegevens uit het Toernooi-object halen
                Label id = new Label("ID: " + Integer.toString(toernooi.getId()));
                Label naam = new Label("Toernooi: " + toernooi.getNaam());
                Label datum = new Label("Datum: " + toernooi.getDatum());

                // Ruimte rond de labels
                id.setPadding(new Insets(5, 12, 0, 12));
                naam.setPadding(new Insets(5, 12, 0, 12));
                datum.setPadding(new Insets(5, 12, 0, 12));

                // Een horizontale layout maken voor één toernooi
                HBox toernooiRow = new HBox();
                toernooiRow.setSpacing(15);
                toernooiRow.setPadding(new Insets(10));
                toernooiRow.setAlignment(Pos.CENTER_LEFT);

                Button editButton = new Button("Bewerken");
                editButton.setPrefSize(90, 30);

                editButton.setStyle(
                        "-fx-background-color: #eeeeee;" +
                                "-fx-text-fill: #333333;" +
                                "-fx-background-radius: 5;" +
                                "-fx-cursor: hand;"
                );

                editButton.setOnAction(event -> {
                    EditTournamentScreen editTournamentScreen = new EditTournamentScreen();
                    editTournamentScreen.show(stage, tournamentScreen, toernooi);
                });

                Button deleteButton = new Button("Verwijderen");
                deleteButton.setPrefSize(100, 30);

                deleteButton.setStyle(
                        "-fx-background-color: #ff6500;" +
                                "-fx-text-fill: white;" +
                                "-fx-background-radius: 5;" +
                                "-fx-cursor: hand;"
                );

                deleteButton.setOnAction(event -> {
                    String sql = "DELETE FROM toernooien WHERE id = ?";

                    try (Connection deleteConnection = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/ea_sports_fc",
                            "root",
                            "");
                         PreparedStatement deleteStatement =
                                 deleteConnection.prepareStatement(sql)) {

                        deleteStatement.setInt(1, toernooi.getId());

                        int rowsDeleted = deleteStatement.executeUpdate();

                        if (rowsDeleted > 0) {
                            System.out.println("Toernooi succesvol verwijderd!");

                            // Toernooienoverzicht opnieuw laden
                            new TournamentScreen().show(stage, homeScreen);

                        } else {
                            System.out.println("Toernooi niet gevonden.");
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

                toernooiRow.getChildren().addAll(id, naam, datum, editButton, deleteButton);

                // De volledige rij toevoegen aan de hoofd-layout
                layout.getChildren().add(toernooiRow);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        stage.setTitle("Toernooien pagina");
        stage.setScene(tournamentScreen );
    }
}
