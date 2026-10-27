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

        stage.setTitle("Deelnamens pagina");
        stage.setScene(participationScreen );
    }
}
