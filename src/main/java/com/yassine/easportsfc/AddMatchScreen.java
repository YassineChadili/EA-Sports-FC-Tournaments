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

public class AddMatchScreen {
    public void show(Stage stage, Scene matchScreen ) {
        VBox layout = new VBox();
        layout.setSpacing(15);

        // Titel toevoegen
        Label title = new Label("Partij aanmaken:");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        title.setPadding(new Insets(15, 12, 15, 12));

        // Invoerveld
        TextField player1Field = new TextField();
        player1Field.setPromptText("Speler 1");

        TextField player2Field = new TextField();
        player2Field.setPromptText("Speler 2");

        TextField tournamentField = new TextField();
        tournamentField.setPromptText("Toernooi");

        TextField scoreField = new TextField();
        scoreField.setPromptText("Uitslag");

        // Horizontale rij met knoppen
        HBox nav = new HBox();
        nav.setPadding(new Insets(15, 12, 15, 12));
        nav.setSpacing(10);
        nav.setStyle("-fx-background-color: #ff6500;");
        Button backButton = new Button("Terug");
        backButton.setPrefSize(100, 20);

        // Terugknop
        backButton.setOnAction(event -> {
            stage.setScene(matchScreen);
            stage.setTitle("Partijen pagina");
        });

        nav.getChildren().addAll(backButton);

        // Opslaan-knop
        Button saveButton = new Button("Opslaan");

        // Alles toevoegen aan de layout
        layout.getChildren().addAll(nav, title, player1Field, player2Field, tournamentField, scoreField, saveButton);

        Scene addTournamentScreen  = new Scene(layout, 800, 500);
        stage.setTitle("Partij aanmaak pagina");
        stage.setScene(addTournamentScreen );
    }
}
