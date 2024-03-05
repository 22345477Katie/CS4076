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
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


/**
 * JavaFX App
 */
public class App extends Application {
  static InetAddress host;
  static final int PORT = 1234;
  Label label = new Label("Response From Server Will Display Here");
  Label title = new Label("Welcome to your class scheduler !");
  Label labelDisplay;
  Label classLabel;
  Label labelDelete;
  Label titleDisplay;
  TextField textFieldRoomDisplay;
  TextField moduleCode;
  ChoiceBox dayOfWeek;
  ChoiceBox startTime;
  ChoiceBox endTime;
  TextField roomCode;
  Button confirmAdd;
  Button confirmRemove;
  String transmission;
  Button buttonAdd = new Button("Add class");
  Button buttonRemove = new Button("Remove class");
  Button buttonDisplay = new Button("Display schedule");
  Button confirmDisplay;
  Button buttonQuit = new Button("STOP");
  

    @Override
    public void start(Stage stage) {

        title.setStyle("-fx-text-fill: #002633; -fx-font-weight: bold; "
                    + "-fx-font-size: 20px");
        buttonAdd.setStyle("-fx-background-color: #002633; -fx-text-fill: #ffffff; -fx-font-weight: bold");
        buttonRemove.setStyle("-fx-background-color: #002633; -fx-text-fill: #ffffff;  -fx-font-weight: bold");
        buttonDisplay.setStyle("-fx-background-color: #002633; -fx-text-fill: #ffffff;  -fx-font-weight: bold");
        label.setStyle("-fx-text-style: italic");
        VBox box= new VBox(title, buttonAdd, buttonRemove, buttonDisplay, buttonQuit /* label*/);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);
        var scene = new Scene(box, 640, 480);
        
        Button buttonHome = new Button ("Back to home");
        buttonHome.setStyle("-fx-background-color: #002633; -fx-text-fill: white;"
                    + "-fx-border-color: white");
        
        

        buttonAdd.setOnAction(new EventHandler<ActionEvent>(){
        @Override
          public void handle(ActionEvent t){
              try{
                  host = InetAddress.getLocalHost();
              }
              catch(UnknownHostException e){
                  System.out.println("Host ID not found!");
                  System.exit(1);
              }
              
              classLabel = new Label ("Please enter the following details about the class you wish to add.");
              moduleCode = new TextField("Module code (eg. CS4076)");
              dayOfWeek = new ChoiceBox();
              dayOfWeek.getItems().add("Monday");
              dayOfWeek.getItems().add("Tuesday");
              dayOfWeek.getItems().add("Wednesday");
              dayOfWeek.getItems().add("Thursday");
              dayOfWeek.getItems().add("Friday");
              startTime = new ChoiceBox();
              for (int i = 9; i<18; i++){
                  startTime.getItems().add(i+":00");
              }
              endTime = new ChoiceBox();
              for (int i = 10; i<19; i++){
                  endTime.getItems().add(i+":00");
              }

              roomCode = new TextField("Room Code (eg. CSG001)");
              confirmAdd = new Button("Add Class");
              
              Button buttonHome = new Button ("Back to home");
              buttonHome.setOnAction(new EventHandler<ActionEvent> (){
                            
                @Override
                public void handle(ActionEvent t){
                    stage.setScene(scene);
                }
                
            });
             
            
            confirmAdd.setOnAction(new EventHandler<ActionEvent> () {
               
               @Override
               public void handle(ActionEvent t){
                    try 
                    {
                        Socket link = new Socket(host,PORT);
                        BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
                        PrintWriter out = new PrintWriter(link.getOutputStream(),true);
                        
                        
                        String response= null;
                        
                        String[] startTimeSplit = startTime.getValue().toString().split(":");
                        String[] endTimeSplit = endTime.getValue().toString().split(":");
                        String message = "0/"+ moduleCode.getText()+"/"+dayOfWeek.getValue()+"/"+startTimeSplit[0]+"/"+endTimeSplit[0]+"/"+roomCode.getText();
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
                    Button buttonAddHome = new Button ("Back to home");
            buttonAddHome.setOnAction(new EventHandler<ActionEvent> (){
                            
                @Override
                public void handle(ActionEvent t){
                    stage.setScene(scene);
                }
            });
                    VBox box = new VBox(label, buttonAddHome);
               var sceneConfirmAdd = new Scene(box, 640, 480);
               stage.setScene (sceneConfirmAdd);
               }
               
           });
            VBox box = new VBox(classLabel, moduleCode, dayOfWeek, startTime, endTime, roomCode,confirmAdd, buttonHome);
            var sceneAdd = new Scene(box, 640, 480);
            stage.setScene(sceneAdd);
          }  
        });
        
        buttonRemove.setOnAction(new EventHandler<ActionEvent>(){
        @Override
          public void handle(ActionEvent t){
              try{
                  host = InetAddress.getLocalHost();
              }
              catch(UnknownHostException e){
                  System.out.println("Host ID not found!");
                  System.exit(1);
              }
              
              labelDelete = new Label ("Please enter the details of the class you wish to remove.");
              moduleCode = new TextField("Module code (eg. CS4076)");
              dayOfWeek = new ChoiceBox();
              dayOfWeek.getItems().add("Monday");
              dayOfWeek.getItems().add("Tuesday");
              dayOfWeek.getItems().add("Wednesday");
              dayOfWeek.getItems().add("Thursday");
              dayOfWeek.getItems().add("Friday");
              startTime = new ChoiceBox();
              for (int i = 9; i<18; i++){
                  startTime.getItems().add(i+":00");
              }
              endTime = new ChoiceBox();
              for (int i = 10; i<19; i++){
                  endTime.getItems().add(i+":00");
              }

              roomCode = new TextField("Room Code (eg. CSG001)");
              confirmRemove = new Button("Remove Class");
              
              Button buttonHome = new Button ("Back to home");
              buttonHome.setOnAction(new EventHandler<ActionEvent> (){
                            
                @Override
                public void handle(ActionEvent t){
                    stage.setScene(scene);
                }
                
            });
             
            
            confirmRemove.setOnAction(new EventHandler<ActionEvent> () {
               
               @Override
               public void handle(ActionEvent t){
                    try 
                    {
                        Socket link = new Socket(host,PORT);
                        BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
                        PrintWriter out = new PrintWriter(link.getOutputStream(),true);
                        
                        
                        String response= null;
                        
                        String[] startTimeSplit = startTime.getValue().toString().split(":");
                        String[] endTimeSplit = endTime.getValue().toString().split(":");
                        String message = "1/"+ moduleCode.getText()+"/"+dayOfWeek.getValue()+"/"+startTimeSplit[0]+"/"+endTimeSplit[0]+"/"+roomCode.getText();
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
                    Button buttonRemoveHome = new Button ("Back to home");
            buttonRemoveHome.setOnAction(new EventHandler<ActionEvent> (){
                            
                @Override
                public void handle(ActionEvent t){
                    stage.setScene(scene);
                }
            });
                    VBox box = new VBox(label, buttonRemoveHome);
               var sceneConfirmRemove = new Scene(box, 640, 480);
               stage.setScene (sceneConfirmRemove);
               }
               
           });
            VBox box = new VBox(labelDelete, moduleCode, dayOfWeek, startTime, endTime, roomCode,confirmRemove, buttonHome);
            var sceneRemove = new Scene(box, 640, 480);
            stage.setScene(sceneRemove);
          }  
        });
        
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
           titleDisplay = new Label("Display schedule");
           labelDisplay = new Label ("ENTER CLASS CODE"); 
           textFieldRoomDisplay = new TextField("");
           confirmDisplay = new Button ("Show schedule");
           
           //Button back to home
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
                    if(textFieldRoomDisplay.getText().trim().isEmpty()){
                        label.setText("Please enter the class code !");
                    } else {
                        try 
                        {
                            Socket link = new Socket(host,PORT);
                            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
                            PrintWriter out = new PrintWriter(link.getOutputStream(),true);


                            String response= null;

                            System.out.println("Enter message to be sent to server: ");
                            String classCode = textFieldRoomDisplay.getText();
                            // 2 for the action, the / is to split the texts
                            out.println("2/" + classCode);
                            response = in.readLine();
                            String[] foundClasses = response.split("-");
                            
                            if(foundClasses[0].equals("CLASS")){
                                
                                //A class or more has been found with this code
                                label.setText("Displaying the schedule for the class " + classCode);
                                
                                GridPane gridPane = new GridPane();
                                gridPane.setPadding(new Insets(10, 10, 10, 10)); // Padding around the grid
                                
                                gridPane.setGridLinesVisible(true);
                              
                                // Set the GridPane to expand and fill the available space, equalizing column widths
                                ColumnConstraints columnConstraints = new ColumnConstraints();
                                columnConstraints.setPercentWidth(90.0 / 5); //Having 5 days during the week
                                gridPane.getColumnConstraints().addAll(columnConstraints, 
                                        columnConstraints, columnConstraints, 
                                        columnConstraints, columnConstraints,
                                        columnConstraints);
                                
                                //Row constraints
                                RowConstraints rowConstraints = new RowConstraints();
                                rowConstraints.setPercentHeight(80.0 / 10); //Having 10 timeslots in a day
                                
                                gridPane.getRowConstraints().addAll(rowConstraints, 
                                        rowConstraints, rowConstraints, rowConstraints, 
                                        rowConstraints, rowConstraints, rowConstraints, 
                                        rowConstraints, rowConstraints, rowConstraints, 
                                        rowConstraints);
                                
                                // Days of the week headers
                                String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
                                for (int i = 0; i < daysOfWeek.length; i++) {
                                    Label dayLabel = new Label(daysOfWeek[i]);
                                    dayLabel.setAlignment(Pos.CENTER); // Center align the text
                                    dayLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Allow the label to expand fully
                                    GridPane.setFillWidth(dayLabel, true); // Stretch label to fill cell width
                                    GridPane.setFillHeight(dayLabel, true); // Stretch label to fill cell height
                                    dayLabel.setStyle("-fx-background-color: #002633; "
                                            + "-fx-text-fill: #ffffff; "
                                            + "-fx-border-color: black;"
                                            + "-fx-border-width: 1;"
                                            + "-fx-border-style: solid;");
                                    GridPane.setHalignment(dayLabel, HPos.CENTER); // Center align the label within the grid cell
                                    GridPane.setValignment(dayLabel, VPos.CENTER); // Vertically center the label within the grid cell
                                    gridPane.add(dayLabel, i + 1, 0); // Offset by 1 to leave room for the time column
                                }

                                // Time slots
                                String[] timeSlots = {"9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"};
                                for (int i = 0; i < timeSlots.length; i++) {
                                    Label timeLabel = new Label(timeSlots[i]);
                                    timeLabel.setAlignment(Pos.CENTER); // Center align the text
                                    timeLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Allow the label to expand fully
                                    GridPane.setFillWidth(timeLabel, true); // Stretch label to fill cell width
                                    GridPane.setFillHeight(timeLabel, true); // Stretch label to fill cell height
                                    timeLabel.setStyle("-fx-background-color: #002633; "
                                            + "-fx-text-fill: #ffffff; "
                                            + "-fx-border-color: black;"
                                            + "-fx-border-width: 1;"
                                            + "-fx-border-style: solid;");
                                    GridPane.setHalignment(timeLabel, HPos.CENTER); // Center align the label within the grid cell
                                    GridPane.setValignment(timeLabel, VPos.CENTER); // Vertically center the label within the grid cell
                                    gridPane.add(timeLabel, 0, i + 1); // Offset by 1 to leave room for the days row
                                }
                                
                                for(int i = 1; i < foundClasses.length; i ++){
                                    //We start at 1 because 0 is taken by "CLASS"
                                    String myClass[] = foundClasses[i].split("/");
                                    String code = myClass[0];
                                    String room = myClass[1];
                                    int day = Integer.valueOf(myClass[2]);
                                    int startingTime = Integer.valueOf(myClass[3]);
                                    int endingTime = Integer.valueOf(myClass[4]);
                                    
                                    Label myClassLabel = new Label(code + "\n(" + room + ")");
                                    myClassLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Allow the label to expand fully
                                    GridPane.setFillWidth(myClassLabel, true); // Stretch label to fill cell width
                                    GridPane.setFillHeight(myClassLabel, true); // Stretch label to fill cell height
                                    myClassLabel.setStyle("-fx-background-color: #ffcc99; "
                                            + "-fx-alignment: center; "
                                            + "-fx-border-color: black;"
                                            + "-fx-border-width: 1;"
                                            + "-fx-border-style: solid;");
                                    gridPane.add(myClassLabel, day, startingTime-8, 1, endingTime - startingTime);
                                }

                                
                                //Button back to home
                                buttonHome.setOnAction(new EventHandler<ActionEvent> (){

                                    @Override
                                    public void handle(ActionEvent t){
                                            stage.setScene(scene);
                                        }
                                });
                               

                                // Setting the scene
                                VBox boxSchedule = new VBox();
                                HBox homeButtonContainer = new HBox(buttonHome);
                                homeButtonContainer.setAlignment(Pos.CENTER);
                                // Add GridPane and Button to the VBox
                                boxSchedule.getChildren().addAll(gridPane, homeButtonContainer);
                                // Set the VBox to stretch the GridPane vertically to take up all available space
                                VBox.setVgrow(gridPane, Priority.ALWAYS);
                                
                                var sceneDisplay = new Scene(boxSchedule, 800, 600);
                                stage.setScene(sceneDisplay);
                            } else {
                                //No class has been found with this code
                                label.setText(response);
                            }


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
               }
           });
           
            GridPane content = new GridPane();
            
            // Set the GridPane to expand and fill the available space, equalizing column widths
            ColumnConstraints columnConstraintsContent = new ColumnConstraints();
            columnConstraintsContent.setPercentWidth(100.0);
            content.getColumnConstraints().addAll(columnConstraintsContent);
                                
            //Row constraints
            RowConstraints rowConstraintsContent = new RowConstraints();
            rowConstraintsContent.setPercentHeight(80.0 / 6);
                                
            content.getRowConstraints().addAll(rowConstraintsContent, rowConstraintsContent,
                    rowConstraintsContent, rowConstraintsContent, rowConstraintsContent, rowConstraintsContent);
            
            content.setPadding(new Insets(10, 10, 10, 10));
            
            titleDisplay.setStyle("-fx-text-fill: #002633; -fx-font-weight: bold; "
                    + "-fx-font-size: 24px");
            
            content.add(titleDisplay, 0, 0);
            
            GridPane.setHalignment(titleDisplay, HPos.CENTER);
            GridPane.setValignment(titleDisplay, VPos.CENTER);
            
            content.add(labelDisplay, 0, 1);
            GridPane.setHalignment(labelDisplay, HPos.CENTER);
            GridPane.setValignment(labelDisplay, VPos.CENTER);
            
            content.add(textFieldRoomDisplay, 0, 2);
            GridPane.setHalignment(textFieldRoomDisplay, HPos.CENTER);
            GridPane.setValignment(textFieldRoomDisplay, VPos.CENTER);
            
            confirmDisplay.setStyle("-fx-text-fill: #002633; -fx-border-color: #002633");
            
            content.add(confirmDisplay, 0, 4);
            GridPane.setHalignment(confirmDisplay, HPos.CENTER);
            GridPane.setValignment(confirmDisplay, VPos.CENTER);
            
            label.setStyle("-fx-font-style: italic");
            
            content.add(label, 0, 6);
            GridPane.setHalignment(label, HPos.CENTER);
            GridPane.setValignment(label, VPos.CENTER);
            
            BorderPane borderPane = new BorderPane();
            
            borderPane.setPadding(new Insets(20, 100, 20, 100));
            
            borderPane.setCenter(content);
            
            HBox homeButtonContainer = new HBox(buttonHome);
            homeButtonContainer.setAlignment(Pos.CENTER);
            borderPane.setBottom(homeButtonContainer);
            
            
            var sceneDisplay = new Scene(borderPane, 640, 480);
            stage.setScene(sceneDisplay);
           

           
        }});
        
        buttonQuit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t){
                try{
                    try{
                        host = InetAddress.getLocalHost();
                    }catch(UnknownHostException e){
                        System.out.println("Host ID not found!");
                        System.exit(1);
                    }
                    Socket link = new Socket(host,PORT);
                    BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
                    PrintWriter out = new PrintWriter(link.getOutputStream(),true);
                    String response= null;
                    out.println("3");
                    response = in.readLine();
                    if(response.equals("TERMINATE")){
                        try{
                            link.close();
                            stage.close();
                        }catch(IOException ex){
                            System.exit(1);
                        }                          
                    }                      
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }       
        });
        
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}