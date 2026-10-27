package com.yassine.easportsfc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeScreen extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        VBox layout = new VBox();

        // Titel toevoegen
        Label title = new Label("EA Sports FC toernooien app!");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        title.setPadding(new Insets(15, 12, 15, 12));

        Label description = new Label("Met deze applicatie kun je EA Sports FC-toernooien eenvoudig beheren.\n" +
                        "Registreer spelers, maak toernooien aan, beheer deelnames\n" +
                        "en sla partijen met hun uitslagen op.\n\n" +
                        "Kies een pagina om te beginnen."
        );

        description.setWrapText(true);
        description.setPadding(new Insets(10, 12, 15, 12));

        // Horizontale rij met knoppen (navigatie)
        HBox nav = new HBox();
        nav.setPadding(new Insets(15, 12, 15, 12));
        nav.setSpacing(10);
        nav.setStyle("-fx-background-color: #ff6500;");
        Button playerScreenButton = new Button("Spelers pagina");
        Button tournamentScreenButton = new Button("Toernooien pagina");
        Button participationScreenButton = new Button("Deelnames pagina");
        Button matchScreenButton = new Button("Partijen pagina");
        playerScreenButton.setPrefSize(100, 20);
        tournamentScreenButton.setPrefSize(100, 20);
        participationScreenButton.setPrefSize(100, 20);
        matchScreenButton.setPrefSize(100, 20);

        Scene homeScreen = new Scene(layout, 800, 500);

        nav.getChildren().addAll(playerScreenButton, tournamentScreenButton, participationScreenButton, matchScreenButton);

        // HBox toevoegen aan VBox
        layout.getChildren().addAll(nav, title, description);

        stage.setTitle("Home pagina");
        stage.setScene(homeScreen);
        stage.show();
    }
}
