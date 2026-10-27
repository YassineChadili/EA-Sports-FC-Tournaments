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

        stage.setTitle("Partijen pagina");
        stage.setScene(matchScreen );
    }
}
