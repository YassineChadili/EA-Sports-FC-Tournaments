package com.yassine.easportsfc;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.*;

public class PlayerScreen {
    public void show(Stage stage, Scene homeScreen ) {
        VBox layout = new VBox();

        // Titel toevoegen
        Label title = new Label("Spelers overzicht:");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        title.setPadding(new Insets(15, 12, 15, 12));

        Label description = new Label("Hieronder staan aangemelde spelers");
        description.setPadding(new Insets(10, 12, 15, 12));

        // Horizontale rij met knoppen
        HBox nav = new HBox();
        nav.setPadding(new Insets(15, 12, 15, 12));
        nav.setSpacing(10);
        nav.setStyle("-fx-background-color: #ff6500;");
        Button backButton = new Button("Terug");
        backButton.setPrefSize(130, 35);
        backButton.setStyle("-fx-background-color: white; -fx-text-fill: #333333;");
        Button playerAdd = new Button("Speler aanmaken");
        playerAdd.setPrefSize(130, 35);
        playerAdd.setStyle("-fx-background-color: white; -fx-text-fill: #333333;");

        Scene playerScreen  = new Scene(layout, 800, 500);

        // Terugknop
        backButton.setOnAction(event -> {
            stage.setScene(homeScreen);
            stage.setTitle("Home");
        });

        playerAdd.setOnAction(event -> {
            AddPlayerScreen addPlayerScreen = new AddPlayerScreen();
            addPlayerScreen.show(stage, playerScreen);
        });

        nav.getChildren().addAll(backButton, playerAdd);

        // Alles toevoegen aan de layout
        layout.getChildren().addAll(nav, title, description);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ea_sports_fc", "root", "");
            Statement statement = connection.createStatement();
            ResultSet output = statement.executeQuery("SELECT * FROM spelers");
            while(output.next()) {
                // Gegevens uit de database in een Speler-object plaatsen
                Speler speler = new Speler(
                        output.getInt("id"),
                        output.getString("naam"),
                        output.getInt("rating")
                );

                // Gegevens uit het Speler-object halen
                Label id = new Label("ID: " + Integer.toString(speler.getId()));
                Label naam = new Label("Speler: " + speler.getNaam());
                Label rating = new Label("Rating: " + Integer.toString(speler.getRating()));

                // Ruimte rond de labels
                id.setPadding(new Insets(5, 12, 0, 12));
                naam.setPadding(new Insets(5, 12, 0, 12));
                rating.setPadding(new Insets(5, 12, 0, 12));

                // Een horizontale layout maken voor één speler
                HBox playerRow = new HBox();
                playerRow.setSpacing(15);
                playerRow.setPadding(new Insets(10));
                playerRow.setAlignment(Pos.CENTER_LEFT);

                Button editButton = new Button("Bewerken");
                editButton.setPrefSize(90, 30);

                editButton.setStyle(
                        "-fx-background-color: #eeeeee;" +
                                "-fx-text-fill: #333333;" +
                                "-fx-background-radius: 5;" +
                                "-fx-cursor: hand;"
                );

                editButton.setOnAction(event -> {
                    EditPlayerScreen editPlayerScreen = new EditPlayerScreen();
                    editPlayerScreen.show(stage, playerScreen, speler);
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
                    String sql = "DELETE FROM spelers WHERE id = ?";

                    try (Connection deleteConnection  = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/ea_sports_fc",
                            "root",
                            "");
                         PreparedStatement deleteStatement  =
                                 deleteConnection.prepareStatement(sql)) {

                        deleteStatement .setInt(1, speler.getId());

                        int rowsDeleted = deleteStatement .executeUpdate();

                        if (rowsDeleted > 0) {
                            System.out.println("Speler succesvol verwijderd!");

                            // Spelersoverzicht opnieuw laden
                            new PlayerScreen().show(stage, homeScreen);
                        } else {
                            System.out.println("Speler niet gevonden.");
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

                playerRow.getChildren().addAll(id, naam, rating, editButton, deleteButton);

                // De volledige rij toevoegen aan de hoofd-layout
                layout.getChildren().add(playerRow);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Spelers pagina");
        stage.setScene(playerScreen );
    }
}
