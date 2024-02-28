package com.mycompany.hellofx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
  static InetAddress host;
  static final int PORT = 1234;
  Label label = new Label("Response From Server Will Display Here");
  Label labelDisplay;
  TextField textFieldRoomDisplay;
  Button buttonAdd = new Button("Add class");
  Button buttonRemove = new Button("Remove class");
  Button buttonDisplay = new Button("Display schedule");
  Button confirmDisplay;

    @Override
    public void start(Stage stage) {

        
        VBox box= new VBox( buttonAdd, buttonRemove, buttonDisplay, label);
        var scene = new Scene(box, 640, 480);

        
        
        buttonDisplay.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
            try 
            {
               host = InetAddress.getLocalHost();
            } 
            catch(UnknownHostException e) 
            {
               System.out.println("Host ID not found!");
               System.exit(1);
            }
           
           //Opening a new window for the selected action
           //Box with the necessary information
           labelDisplay = new Label ("From what class would you like to see the schedule ?"); 
           textFieldRoomDisplay = new TextField("");
           confirmDisplay = new Button ("Show schedule");
           
           //Button back to home
                        
            Button buttonHome = new Button ("Back to home");
            buttonHome.setOnAction(new EventHandler<ActionEvent> (){
                            
                @Override
                public void handle(ActionEvent t){
                    stage.setScene(scene);
                }
            });
                        
           
           //Action on the confirm button
           confirmDisplay.setOnAction(new EventHandler<ActionEvent> () {
               
               @Override
               public void handle(ActionEvent t){
                    try 
                    {
                        Socket link = new Socket(host,PORT);
                        BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
                        PrintWriter out = new PrintWriter(link.getOutputStream(),true);
                        
                        
                        String response= null;
                        
                        System.out.println("Enter message to be sent to server: ");
                        String message = textFieldRoomDisplay.getText();
                        out.println(message);
                        response = in.readLine();
                        label.setText(response);
                        
                        
                        try
                        {
                            System.out.println("\n* Closing connection... *");
                            link.close();
                        }catch(IOException e)
                        {
                            System.out.println("Unable to disconnect/close!");
                            System.exit(1);
                        }
                    }catch(IOException ex)
                    {
                        ex.printStackTrace();
                    }
                    finally
                    {
                        
                    }
               }
           });
           
                              
            VBox box = new VBox(labelDisplay, textFieldRoomDisplay, confirmDisplay, label, buttonHome);
            var sceneDisplay = new Scene(box, 640, 480);
            stage.setScene(sceneDisplay);
           

           
        }});
        
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}