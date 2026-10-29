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
        backButton.setPrefSize(100, 20);
        Button playerAdd = new Button("Speler aanmaken");
        playerAdd.setPrefSize(100, 20);

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
                // Gegevens uit de database in een Player-object plaatsen
                Player player = new Player(
                        output.getInt("id"),
                        output.getString("naam"),
                        output.getInt("rating")
                );

                // Gegevens uit het Player-object halen
                Label naam = new Label("Speler: " + player.getNaam());
                Label rating = new Label("Rating: " + Integer.toString(player.getRating()));

                // Ruimte rond de labels
                naam.setPadding(new Insets(5, 12, 0, 12));
                rating.setPadding(new Insets(5, 12, 0, 12));

                // Een horizontale layout maken voor één speler
                HBox playerRow = new HBox();
                playerRow.setSpacing(15);

                Button editButton = new Button("Bewerken");

                editButton.setOnAction(event -> {
                    EditPlayerScreen editPlayerScreen = new EditPlayerScreen();
                    editPlayerScreen.show(stage, playerScreen, player);
                });

                Button deleteButton = new Button("Verwijderen");

                deleteButton.setOnAction(event -> {
                    String sql = "DELETE FROM spelers WHERE id = ?";

                    try (Connection deleteConnection  = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/ea_sports_fc",
                            "root",
                            "");
                         PreparedStatement deleteStatement  =
                                 deleteConnection.prepareStatement(sql)) {

                        deleteStatement .setInt(1, player.getId());

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

                playerRow.getChildren().addAll(naam, rating, editButton, deleteButton);

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
