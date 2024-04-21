package com.mycompany.hellofx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.concurrent.Task;



/**
 * JavaFX App
 */
public class App extends Application {
  static InetAddress host;
  static final int PORT = 1234;

  //Main Screen
  Label label = new Label("Response From Server Will Display Here");
  Label title = new Label("Welcome to your class scheduler!");

  //Add Screen
  Label classLabel;
  Label titleAdd;
  TextField courseCode;
  TextField moduleCode;
  ChoiceBox dayOfWeek;
  ChoiceBox startTime;
  ChoiceBox endTime;
  TextField roomCode;
  Button confirmAdd;
  Button buttonAdd = new Button("Add class");
  Label chooseDay;
  Label chooseStart;
  Label chooseEnd;

  //Remove Screen
  Label labelDelete;
  Label titleRemove;
  Button confirmRemove;
  Button buttonRemove = new Button("Remove class");

  //Display Screen
  Label labelDisplay;
  Label titleDisplay;
  TextField textFieldRoomDisplay;
  String transmission;
  Button buttonDisplay = new Button("Display schedule");
  Button confirmDisplay;
  
  //Early Lectures
  Button buttonEarlyLectures = new Button("Early Lectures");
  Button confirmEarlyLectures;
  TextField courseCodeEarlyLectures;
  Label titleEarlyLectures;

  //Quit
  Button buttonQuit = new Button("STOP");


    @Override
    public void start(Stage stage) throws IOException {

        title.setStyle("-fx-text-fill: #002633; -fx-font-weight: bold; "
                    + "-fx-font-size: 20px");
        buttonAdd.setStyle("-fx-background-color: #002633; -fx-text-fill: #ffffff; -fx-font-weight: bold");
        buttonRemove.setStyle("-fx-background-color: #002633; -fx-text-fill: #ffffff;  -fx-font-weight: bold");
        buttonDisplay.setStyle("-fx-background-color: #002633; -fx-text-fill: #ffffff;  -fx-font-weight: bold");
        buttonEarlyLectures.setStyle("-fx-background-color: #002633; -fx-text-fill: #ffffff;  -fx-font-weight: bold");
        buttonQuit.setStyle("-fx-background-color: #002633; -fx-text-fill: #ffffff;  -fx-font-weight: bold");
        //label.setStyle("-fx-text-style: italic");
        VBox box= new VBox(title, buttonAdd, buttonRemove, buttonDisplay, buttonEarlyLectures, buttonQuit /* label*/);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);
        var scene = new Scene(box, 640, 480);



        try{
            host = InetAddress.getLocalHost();//Connect with the server, send the message, receive a response and prepare it for outputting
            Socket link = new Socket(host,PORT);



        buttonAdd.setOnAction(new EventHandler<ActionEvent>(){
        @Override
          public void handle(ActionEvent t){
              //Nodes/input
              titleAdd = new Label("Add Class");
              classLabel = new Label ("Please enter the following details about the class you wish to add.");
              courseCode = new TextField("Course Code (eg. LM051)");
              moduleCode = new TextField("Module Code (eg. CS4076)");
              dayOfWeek = new ChoiceBox();
              dayOfWeek.getItems().add("Monday");
              dayOfWeek.getItems().add("Tuesday");
              dayOfWeek.getItems().add("Wednesday");
              dayOfWeek.getItems().add("Thursday");
              dayOfWeek.getItems().add("Friday");
              //Iterate through to add all the valid start and end times to the ChoiceBoxes
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
              Button buttonHome = new Button("Back to home");
              //Set the home button to return to the main window
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
                        
                        BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
                        PrintWriter out = new PrintWriter(link.getOutputStream(),true);

                        String response= null;

                        String[] startTimeSplit = startTime.getValue().toString().split(":");
                        String[] endTimeSplit = endTime.getValue().toString().split(":");
                        String message = "0/"+ courseCode.getText() + "/" + moduleCode.getText()+"/"+dayOfWeek.getValue()+"/"+startTimeSplit[0]+"/"+endTimeSplit[0]+"/"+roomCode.getText();
                        out.println(message);
                        response = in.readLine();
                        label.setText(response);


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
            GridPane content = new GridPane();

            // Set the GridPane to expand and fill the available space, equalizing column widths
            ColumnConstraints columnConstraintsContent = new ColumnConstraints();
            columnConstraintsContent.setPercentWidth(100.0);
            content.getColumnConstraints().addAll(columnConstraintsContent);

            //Row constraints
            RowConstraints rowConstraintsContent = new RowConstraints();
            rowConstraintsContent.setPercentHeight(80.0 / 1);

            content.getRowConstraints().addAll(rowConstraintsContent, rowConstraintsContent,
                    rowConstraintsContent, rowConstraintsContent, rowConstraintsContent, rowConstraintsContent, rowConstraintsContent);

            content.setPadding(new Insets(10, 10, 10, 10));

            content.add(label, 0, 0);
            GridPane.setHalignment(label, HPos.CENTER);
            GridPane.setValignment(label, VPos.CENTER);

            buttonAddHome.setStyle("-fx-background-color: #002633; -fx-text-fill: #ffffff; -fx-font-weight: bold");
            content.add(buttonAddHome, 0, 1);
            GridPane.setHalignment(buttonAddHome, HPos.CENTER);
            GridPane.setValignment(buttonAddHome, VPos.CENTER);

            BorderPane borderPane = new BorderPane();

            borderPane.setPadding(new Insets(20, 80, 20, 80));

            borderPane.setCenter(content);
               var sceneConfirmAdd = new Scene(borderPane, 640, 480);
               stage.setScene (sceneConfirmAdd);
               }

           });

            GridPane content = new GridPane();

            // Set the GridPane to expand and fill the available space, equalizing column widths
            ColumnConstraints columnConstraintsContent = new ColumnConstraints();
            columnConstraintsContent.setPercentWidth(100.0);
            content.getColumnConstraints().addAll(columnConstraintsContent);

            //Row constraints
            RowConstraints rowConstraintsContent = new RowConstraints();
            rowConstraintsContent.setPercentHeight(80.0 / 11);

            content.getRowConstraints().addAll(rowConstraintsContent, rowConstraintsContent,
                    rowConstraintsContent, rowConstraintsContent, rowConstraintsContent, rowConstraintsContent,
                    rowConstraintsContent, rowConstraintsContent, rowConstraintsContent, rowConstraintsContent, rowConstraintsContent);

            content.setPadding(new Insets(10, 10, 10, 10));

            titleAdd.setStyle("-fx-text-fill: #002633; -fx-font-weight: bold; "
                    + "-fx-font-size: 24px");

            content.add(titleAdd, 0, 0);

            GridPane.setHalignment(titleAdd, HPos.CENTER);
            GridPane.setValignment(titleAdd, VPos.CENTER);

            content.add(classLabel, 0, 1);
            GridPane.setHalignment(classLabel, HPos.CENTER);
            GridPane.setValignment(classLabel, VPos.CENTER);
            
            content.add(courseCode, 0, 2);
            GridPane.setHalignment(courseCode, HPos.CENTER);
            GridPane.setValignment(courseCode, VPos.CENTER);

            content.add(moduleCode, 0, 3);
            GridPane.setHalignment(moduleCode, HPos.CENTER);
            GridPane.setValignment(moduleCode, VPos.CENTER);

            chooseDay = new Label("Please choose the day the class will occur on.");
            content.add(chooseDay, 0, 4);
            GridPane.setHalignment(chooseDay, HPos.LEFT);
            GridPane.setValignment(chooseDay, VPos.CENTER);

            content.add(dayOfWeek, 0, 4);
            GridPane.setHalignment(dayOfWeek, HPos.RIGHT);
            GridPane.setValignment(dayOfWeek, VPos.CENTER);

            chooseStart = new Label("Please choose the time the class will START at.");
            content.add(chooseStart, 0, 5);
            GridPane.setHalignment(chooseStart, HPos.LEFT);
            GridPane.setValignment(chooseStart, VPos.CENTER);

            content.add(startTime, 0, 5);
            GridPane.setHalignment(startTime, HPos.RIGHT);
            GridPane.setValignment(startTime, VPos.CENTER);

            chooseEnd = new Label("Please choose the time the class will END at.");
            content.add(chooseEnd, 0, 6);
            GridPane.setHalignment(chooseEnd, HPos.LEFT);
            GridPane.setValignment(chooseEnd, VPos.CENTER);

            content.add(endTime, 0, 6);
            GridPane.setHalignment(endTime, HPos.RIGHT);
            GridPane.setValignment(endTime, VPos.CENTER);

            content.add(roomCode, 0, 7);
            GridPane.setHalignment(roomCode, HPos.CENTER);
            GridPane.setValignment(roomCode, VPos.CENTER);

            confirmAdd.setStyle("-fx-text-fill: #002633; -fx-border-color: #002633");

            content.add(confirmAdd, 0,8);
            GridPane.setHalignment(confirmAdd, HPos.CENTER);
            GridPane.setValignment(confirmAdd, VPos.CENTER);

            buttonHome.setStyle("-fx-background-color: #002633; -fx-text-fill: #ffffff; -fx-font-weight: bold");
            content.add(buttonHome, 0, 9);
            GridPane.setHalignment(buttonHome, HPos.CENTER);
            GridPane.setValignment(buttonHome, VPos.CENTER);

            BorderPane borderPane = new BorderPane();

            borderPane.setPadding(new Insets(20, 100, 20, 100));

            borderPane.setCenter(content);

            var sceneAdd = new Scene(borderPane, 640, 480);
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
              //Nodes/input
              titleRemove = new Label("Remove Class");
              labelDelete = new Label ("Please enter the details of the class you wish to remove.");
              courseCode = new TextField("Course Code (eg. LM051)");
              moduleCode = new TextField("Module code (eg. CS4076)");
              dayOfWeek = new ChoiceBox();
              dayOfWeek.getItems().add("Monday");
              dayOfWeek.getItems().add("Tuesday");
              dayOfWeek.getItems().add("Wednesday");
              dayOfWeek.getItems().add("Thursday");
              dayOfWeek.getItems().add("Friday");
              //Iterate through to add all valid times to the ChoiceBoxes
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

              //Button to return to the main window
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
                        BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
                        PrintWriter out = new PrintWriter(link.getOutputStream(),true);
                        String response= null;

                        String[] startTimeSplit = startTime.getValue().toString().split(":");
                        String[] endTimeSplit = endTime.getValue().toString().split(":");
                        int dayNumber;
                        String dayString = (String) dayOfWeek.getValue();
                        switch(dayString){
                            case "Monday":
                                dayNumber = 1;
                                break;
                            case "Tuesday":
                                dayNumber = 2;
                                break;
                            case "Wednesday":
                                dayNumber = 3;
                                break;
                            case "Thursday":
                                dayNumber = 4;
                                break;
                            default:
                                dayNumber = 5;
                                break;
                        }
                        String message = "1/"+ courseCode.getText() + "/" +moduleCode.getText()+"/"+dayNumber+"/"+startTimeSplit[0]+"/"+endTimeSplit[0]+"/"+roomCode.getText();
                        out.println(message);
                        response = in.readLine();
                        label.setText(response);


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
            GridPane content = new GridPane();

            // Set the GridPane to expand and fill the available space, equalizing column widths
            ColumnConstraints columnConstraintsContent = new ColumnConstraints();
            columnConstraintsContent.setPercentWidth(100.0);
            content.getColumnConstraints().addAll(columnConstraintsContent);

            //Row constraints
            RowConstraints rowConstraintsContent = new RowConstraints();
            rowConstraintsContent.setPercentHeight(80.0 / 1);

            content.getRowConstraints().addAll(rowConstraintsContent, rowConstraintsContent,
                    rowConstraintsContent, rowConstraintsContent, rowConstraintsContent, rowConstraintsContent);

            content.setPadding(new Insets(10, 10, 10, 10));

            content.add(label, 0, 0);
            GridPane.setHalignment(label, HPos.CENTER);
            GridPane.setValignment(label, VPos.CENTER);

            buttonRemoveHome.setStyle("-fx-background-color: #002633; -fx-text-fill: #ffffff; -fx-font-weight: bold");
            content.add(buttonRemoveHome, 0, 1);
            GridPane.setHalignment(buttonRemoveHome, HPos.CENTER);
            GridPane.setValignment(buttonRemoveHome, VPos.CENTER);

            BorderPane borderPane = new BorderPane();

            borderPane.setPadding(new Insets(20, 80, 20, 80));

            borderPane.setCenter(content);
               var sceneConfirmDelete = new Scene(borderPane, 640, 480);
            stage.setScene(sceneConfirmDelete);
               }

           });
            GridPane content = new GridPane();

            // Set the GridPane to expand and fill the available space, equalizing column widths
            ColumnConstraints columnConstraintsContent = new ColumnConstraints();
            columnConstraintsContent.setPercentWidth(100.0);
            content.getColumnConstraints().addAll(columnConstraintsContent);

            //Row constraints
            RowConstraints rowConstraintsContent = new RowConstraints();
            rowConstraintsContent.setPercentHeight(80.0 / 8);

            content.getRowConstraints().addAll(rowConstraintsContent, rowConstraintsContent,
                    rowConstraintsContent, rowConstraintsContent, rowConstraintsContent, rowConstraintsContent,
                    rowConstraintsContent, rowConstraintsContent, rowConstraintsContent, rowConstraintsContent, rowConstraintsContent);

            content.setPadding(new Insets(10,10,10,10));

            titleRemove.setStyle("-fx-text-fill: #002633; -fx-font-weight: bold; "
                    + "-fx-font-size: 24px");

            content.add(titleRemove, 0, 0);

            GridPane.setHalignment(titleRemove, HPos.CENTER);
            GridPane.setValignment(titleRemove, VPos.CENTER);

            content.add(labelDelete, 0, 1);
            GridPane.setHalignment(labelDelete, HPos.CENTER);
            GridPane.setValignment(labelDelete, VPos.CENTER);
            
            content.add(courseCode, 0, 2);
            GridPane.setHalignment(courseCode, HPos.CENTER);
            GridPane.setValignment(courseCode, VPos.CENTER);

            content.add(moduleCode, 0, 3);
            GridPane.setHalignment(moduleCode, HPos.CENTER);
            GridPane.setValignment(moduleCode, VPos.CENTER);

            chooseDay = new Label("Please choose the day the class will occur on.");
            content.add(chooseDay, 0, 4);
            GridPane.setHalignment(chooseDay, HPos.LEFT);
            GridPane.setValignment(chooseDay, VPos.CENTER);

            content.add(dayOfWeek, 0, 4);
            GridPane.setHalignment(dayOfWeek, HPos.RIGHT);
            GridPane.setValignment(dayOfWeek, VPos.CENTER);

            chooseStart = new Label("Please choose the time the class will START at.");
            content.add(chooseStart, 0, 5);
            GridPane.setHalignment(chooseStart, HPos.LEFT);
            GridPane.setValignment(chooseStart, VPos.CENTER);

            content.add(startTime, 0, 5);
            GridPane.setHalignment(startTime, HPos.RIGHT);
            GridPane.setValignment(startTime, VPos.CENTER);

            chooseEnd = new Label("Please choose the time the class will END at.");
            content.add(chooseEnd, 0, 6);
            GridPane.setHalignment(chooseEnd, HPos.LEFT);
            GridPane.setValignment(chooseEnd, VPos.CENTER);

            content.add(endTime, 0, 6);
            GridPane.setHalignment(endTime, HPos.RIGHT);
            GridPane.setValignment(endTime, VPos.CENTER);

            content.add(roomCode, 0, 7);
            GridPane.setHalignment(roomCode, HPos.CENTER);
            GridPane.setValignment(roomCode, VPos.CENTER);

            confirmRemove.setStyle("-fx-text-fill: #002633; -fx-border-color: #002633");

            content.add(confirmRemove, 0, 8);
            GridPane.setHalignment(confirmRemove, HPos.CENTER);
            GridPane.setValignment(confirmRemove, VPos.CENTER);

            buttonHome.setStyle("-fx-background-color: #002633; -fx-text-fill: #ffffff; -fx-font-weight: bold");
            content.add(buttonHome, 0, 9);
            GridPane.setHalignment(buttonHome, HPos.CENTER);
            GridPane.setValignment(buttonHome, VPos.CENTER);

            BorderPane borderPane = new BorderPane();

            borderPane.setPadding(new Insets(20, 100, 20, 100));

            borderPane.setCenter(content);

            var sceneDelete = new Scene(borderPane, 640, 480);
            stage.setScene(sceneDelete);
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
           labelDisplay = new Label ("ENTER COURSE CODE");
           textFieldRoomDisplay = new TextField("");
           confirmDisplay = new Button ("Show schedule");
           Button buttonHome = new Button("Back to home");
           buttonHome.setStyle("-fx-background-color: #002633; -fx-text-fill: #ffffff; -fx-font-weight: bold");

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
                        label.setText("Please enter the course code!");
                    } else {
                        try
                        {
                            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
                            PrintWriter out = new PrintWriter(link.getOutputStream(),true);
                            String response= null;

                            
                            String classCode = textFieldRoomDisplay.getText();
                            // 2 for the action, the / is to split the texts
                            out.println("2/" + classCode);
                            response = in.readLine();
                            String[] foundClasses = response.split("-");

                            if(foundClasses[0].equals("CLASS")){

                                //A class or more has been found with this code
                                label.setText("Displaying the schedule for the course " + classCode);

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
                                rowConstraints.setPercentHeight(80.0 / 9); //Having 9 timeslots in a day

                                gridPane.getRowConstraints().addAll(rowConstraints,
                                        rowConstraints, rowConstraints, rowConstraints,
                                        rowConstraints, rowConstraints, rowConstraints,
                                        rowConstraints, rowConstraints, rowConstraints);

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
                                String[] timeSlots = {"9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00"};
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
                                    String moduleCode = myClass[0];
                                    String room = myClass[1];
                                    int day = Integer.valueOf(myClass[2]);
                                    int startingTime = Integer.valueOf(myClass[3]);
                                    int endingTime = Integer.valueOf(myClass[4]);

                                    Label myClassLabel = new Label(moduleCode + "\n(" + room + ")");
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
        
        buttonEarlyLectures.setOnAction(new EventHandler<ActionEvent>(){
        @Override
          public void handle(ActionEvent t){
              //Nodes/input
              
              titleEarlyLectures = new Label("Which course and day would you like to request early lectures for?");
              courseCodeEarlyLectures = new TextField("Course Code eg. LM051");
              //Iterate through to add all the valid start and end times to the ChoiceBox
              Button buttonHome = new Button("Back to home");
              confirmEarlyLectures = new Button("Confirm Early Lectures");
              //Set the home button to return to the main window
              buttonHome.setOnAction(new EventHandler<ActionEvent> (){

                @Override
                public void handle(ActionEvent t){
                    stage.setScene(scene);
                }

            });

            Task<String> earlyLecturesTask = new Task<>() {
                @Override
                protected String call() throws Exception {
                    //Simulate a long processing time
                    TimeUnit.SECONDS.sleep(10);
                    // Initialize server communication objects
                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
                        PrintWriter out = new PrintWriter(link.getOutputStream(), true);

                        String response;
                        String message = "3/"+ courseCodeEarlyLectures.getText();
                        out.println(message);

                        // Wait for and return the response
                        return in.readLine();
                    } catch (IOException ex) {
                        // Handle exceptions if something goes wrong with I/O
                        System.err.println("Error communicating with the server: " + ex.getMessage());
                        throw ex;
                    }
                }
            };
  

            confirmEarlyLectures.setOnAction(event -> {
                // Start the task
                new Thread(earlyLecturesTask).start();
                //Back to homepage to keep working on other things
                stage.setScene(scene);

                // Bind task's status to UI components
                earlyLecturesTask.setOnSucceeded(e -> {
                    Toast.showToast("Early lectures scheduled successfully!");
                });
                earlyLecturesTask.setOnFailed(e -> {
                    Toast.showToast("Failed to schedule early lectures.");
                });
            });

            GridPane content = new GridPane();

            // Set the GridPane to expand and fill the available space, equalizing column widths
            ColumnConstraints columnConstraintsContent = new ColumnConstraints();
            columnConstraintsContent.setPercentWidth(100.0);
            content.getColumnConstraints().addAll(columnConstraintsContent);

            //Row constraints
            RowConstraints rowConstraintsContent = new RowConstraints();
            rowConstraintsContent.setPercentHeight(80.0 / 5);

            content.getRowConstraints().addAll(rowConstraintsContent, rowConstraintsContent,
                    rowConstraintsContent, rowConstraintsContent, rowConstraintsContent, rowConstraintsContent);

            content.setPadding(new Insets(10, 10, 10, 10));

            titleEarlyLectures.setStyle("-fx-text-fill: #002633; -fx-font-weight: bold; "
                    + "-fx-font-size: 24px");

            content.add(titleEarlyLectures, 0, 0);
            
            content.add(courseCodeEarlyLectures, 0, 1);
            GridPane.setHalignment(courseCodeEarlyLectures, HPos.CENTER);
            GridPane.setValignment(courseCodeEarlyLectures, VPos.CENTER);

            confirmEarlyLectures.setStyle("-fx-background-color: #002633; -fx-text-fill: #ffffff; -fx-font-weight: bold");
            content.add(confirmEarlyLectures, 0, 3);
            GridPane.setHalignment(confirmEarlyLectures, HPos.CENTER);
            GridPane.setValignment(confirmEarlyLectures, VPos.CENTER);
            
            buttonHome.setStyle("-fx-background-color: #002633; -fx-text-fill: #ffffff; -fx-font-weight: bold");
            content.add(buttonHome, 0, 4);
            GridPane.setHalignment(buttonHome, HPos.CENTER);
            GridPane.setValignment(buttonHome, VPos.CENTER);

            BorderPane borderPane = new BorderPane();

            borderPane.setPadding(new Insets(20, 100, 20, 100));

            borderPane.setCenter(content);

            var sceneEarlyLectures = new Scene(borderPane, 640, 480);
            stage.setScene(sceneEarlyLectures);
          }
        });

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
                    BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
                    PrintWriter out = new PrintWriter(link.getOutputStream(),true);
                    String response= null;
                    out.println("4");
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


        catch(UnknownHostException e){
            System.out.println("Host ID not found!");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        launch();
    }

}