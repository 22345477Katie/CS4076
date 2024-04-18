/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hellofx;

import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author traug
 */
public class Toast {
    public static void showToast(String message) {
        Stage toastStage = new Stage();
        toastStage.initStyle(StageStyle.TRANSPARENT); // Use a transparent stage
        toastStage.setAlwaysOnTop(true);
        toastStage.setResizable(false);

        Label text = new Label(message);
        text.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: white; -fx-padding: 10px;");

        StackPane root = new StackPane(text);
        root.setStyle("-fx-background-radius: 5; -fx-background-color: rgba(0, 0, 0, 0.5);");
        Scene scene = new Scene(root);
        scene.setFill(null); // Make the scene background transparent

        toastStage.setScene(scene);
        toastStage.show();

        // Hide the toast after 3 seconds
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> toastStage.close());
        delay.play();
    }
}
