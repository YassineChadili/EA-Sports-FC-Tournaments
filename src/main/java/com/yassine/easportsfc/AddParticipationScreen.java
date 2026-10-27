package com.yassine.easportsfc;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AddParticipationScreen {
    public void show(Stage stage, Scene participationScreen ) {
        VBox layout = new VBox();
        layout.setSpacing(15);

        // Titel toevoegen
        Label title = new Label("Deelnamens aanmaken:");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        title.setPadding(new Insets(15, 12, 15, 12));

        // Invoerveld
        TextField playerField = new TextField();
        playerField.setPromptText("Speler");

        TextField tournamentField = new TextField();
        tournamentField.setPromptText("Toernooi");

        // Horizontale rij met knoppen
        HBox nav = new HBox();
        nav.setPadding(new Insets(15, 12, 15, 12));
        nav.setSpacing(10);
        nav.setStyle("-fx-background-color: #ff6500;");
        Button backButton = new Button("Terug");
        backButton.setPrefSize(100, 20);

        // Terugknop
        backButton.setOnAction(event -> {
            stage.setScene(participationScreen);
            stage.setTitle("Deelnamend pagina");
        });

        nav.getChildren().addAll(backButton);

        // Opslaan-knop
        Button saveButton = new Button("Opslaan");

        // Alles toevoegen aan de layout
        layout.getChildren().addAll(nav, title, playerField, tournamentField, saveButton);

        Scene addParticipationScreen  = new Scene(layout, 800, 500);
        stage.setTitle("Deelname aanmaak pagina");
        stage.setScene(addParticipationScreen );
    }
}
