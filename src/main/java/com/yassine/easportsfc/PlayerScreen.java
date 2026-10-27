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

        stage.setTitle("Spelers pagina");
        stage.setScene(playerScreen );
    }
}
