/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tcpechoserver;

import data.Classes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author traug
 */
public class ServerThread implements Runnable {

    private Socket link;
    private int nclient;
    ArrayList<Classes> classes;
    
    public ServerThread(Socket link, int nClient) {
        this.link = link;
        this.nclient = nClient;
    }
    
    


    @Override
    public void run()
    {
        
        System.out.println("New thread  = TCPrunning");
        classes = TCPEchoServer.getClasses();
        ArrayList<String> moduleCodes = TCPEchoServer.getModuleCodes();
        
        try{
            BufferedReader in = new BufferedReader( new InputStreamReader(link.getInputStream())); //Step 3.
            PrintWriter out = new PrintWriter(link.getOutputStream(),true); //Step 3.
            String message = "";
            
            System.out.println("Waiting for a message");

            String messageClient;         //Step 4.
            while ((messageClient = in.readLine()) != null && !messageClient.equals("quit")) {
                System.out.println("Message received from client: " + nclient + "  "+ messageClient);
                String[] actionClient = messageClient.split("/");

                String action;


                //Receiving the type of action wanted
                switch(actionClient[0]){
                    case "0":
                        action = "add";
                        break;
                    case "1":
                        action = "remove";
                        break;
                    case "2":
                        action = "consult";
                        break;
                    case "3":
                        action = "earlyLectures";
                        break;
                    case "4":
                        action = "quit";
                        break;
                    default:
                        action = null;
                        throw new IncorrectActionException("No action selected.");
                }


                //Depending on the action coming from the client, do the thing
                if(action.equals("add")){
                  try{
                      //Figure out what day it is and make it an int for easier processing
                        int day;
                      switch (actionClient[3]) {
                          case "Monday":
                              day = 1;
                              break;
                          case "Tuesday":
                              day = 2;
                              break;
                          case "Wednesday":
                              day = 3;
                              break;
                          case "Thursday":
                              day = 4;
                              break;
                          default:
                              day = 5;
                              break;
                      }

                      //Create the Classes object
                      Classes scheduledClass = new Classes(actionClient[1], actionClient[2], day, Integer.parseInt(actionClient[4]), Integer.parseInt(actionClient[5]), actionClient[6]);

                      //Ensure the times are valid
                      if(Integer.parseInt(actionClient[5])<=Integer.parseInt(actionClient[4])){
                          throw new IncorrectActionException("The class end time must be after the start time. Class not scheduled.");
                      }

                      //If there are already classes scheduled
                      
                      if(classes.isEmpty()==false){
                          if(moduleCodes.contains(scheduledClass.getClassCode())==false && moduleCodes.size()>=5){
                              //If the list of modules already being taken doesn't contain the given code and there are 5 or more modules already
                              throw new IncorrectActionException("A schedule cannot contain more than 5 modules per semester.");
                          }
                          for (int i = 0; i<classes.size(); i++){
                              //check if the class clashes with any others already scheduled
                              if(scheduledClass.getCourseCode().equals(classes.get(i).getCourseCode())
                                      && scheduledClass.getDay()==classes.get(i).getDay()){
                                  int newStartTime = scheduledClass.getStartingTime();
                                  int newEndTime = scheduledClass.getEndingTime();
                                  int oldStartTime = classes.get(i).getStartingTime();
                                  int oldEndTime = classes.get(i).getEndingTime();
                                  if(newStartTime==oldStartTime
                                      ||newEndTime==oldEndTime
                                      ||(newStartTime>oldStartTime&&newStartTime<oldEndTime)
                                      ||(newEndTime>oldStartTime&&newEndTime<oldEndTime)){
                                          throw new IncorrectActionException("The requested class clashes with an existing class. Class not scheduled.");
                                  }
                              }                                             
                          }
                          //if there are not too many modules and the class does not clash with any others, add it
                          classes.add(scheduledClass);
                          message = "The class was added to the schedule successfully."; 
                      }else{
                          //If there are NOT any classes already scheduled, add it 
                          classes.add(scheduledClass);
                          moduleCodes.add(scheduledClass.getClassCode());
                          message = "The class was added to the schedule successfully.";
                      }
                  }catch (IncorrectActionException e){
                      message = e.getIncorrectActionException();

                  }  
                }

                if(action.equals("remove")){
                    try{
                        //Figure out what day it is and make it an int for easier processing
                        int day;
                      switch (actionClient[3]) {
                          case "Monday":
                              day = 1;
                              break;
                          case "Tuesday":
                              day = 2;
                              break;
                          case "Wednesday":
                              day = 3;
                              break;
                          case "Thursday":
                              day = 4;
                              break;
                          default:
                              day = 5;
                              break;
                      }
                      //Create the Classes object
                      Classes scheduledClass = new Classes(actionClient[1], actionClient[2], day, Integer.parseInt(actionClient[4]), Integer.parseInt(actionClient[5]), actionClient[6]);
                      //No point in checking anything if there aren't any classes yet
                      
                      if(classes.isEmpty()){
                          throw new IncorrectActionException("There are currently no scheduled classes to be removed");
                      }
                      //Check if any of the classes in the ArrayList exactly match what is given, allowing for extra spaces with the .trim()
                      for(int i = 0; i<classes.size(); i++){
                          if(scheduledClass.getCourseCode().equals(classes.get(i).getCourseCode())
                                  && scheduledClass.getClassCode().trim().equals(classes.get(i).getClassCode())
                                  &&scheduledClass.getDay()==classes.get(i).getDay()
                                  &&scheduledClass.getStartingTime()==classes.get(i).getStartingTime()
                                  &&scheduledClass.getEndingTime()==classes.get(i).getEndingTime()
                                  &&scheduledClass.getRoom().trim().equals(classes.get(i).getRoom())){
                              //if the class exists, remove it from the ArrayList
                              classes.remove(i);
                              message = "Class removed successfully!";
                          }else{
                              throw new IncorrectActionException("There is currently no scheduled class that fits the given details.");
                          }
                      }
                    }catch(IncorrectActionException e){
                        message = e.getIncorrectActionException();
                    }
                }

                if(action.equals("consult")){

                    //TEST
                    //Classes classTest = new Classes("CS4076", 1, 9, 11, "CS2044");
                    //classes.add(classTest);
                    //
                    
                    //May need to restructure a little?

                    if(classes.isEmpty()){
                        out.println("The schedule is empty! Add a class first.");
                    } else if (actionClient.length < 1){
                        out.println("No class code found");
                    } else {
                      String classCode = actionClient[1];
                      String schedule = "CLASS";
                      for(int i = 0; i < classes.size(); i ++){
                          if(classes.get(i).getCourseCode().equals(classCode)){
                              schedule += "-" + classes.get(i).getClassCode() + "/"
                                      + classes.get(i).getRoom() + "/" +
                                      classes.get(i).getDay() + "/" +
                                      classes.get(i).getStartingTime() + "/" +
                                      classes.get(i).getEndingTime();
                          }
                          System.out.println("Class " + classes.get(i).getClassCode() + 
                                  " in room " + classes.get(i).getRoom() + " starting at " +
                                  classes.get(i).getStartingTime() + " until " + classes.get(i).getEndingTime()
                                  + " on " + classes.get(i).getDay());
                      }
                      if(schedule.equals("CLASS")){
                          schedule = "No course found with this code";
                      }
                      out.println(schedule);
                    }
                }
                
                if(action.equals("earlyLectures")){
                    TCPEchoServer.setClasses(ForkJoinPool.commonPool().invoke(new EarlyThread.ThreadCreation(classes, actionClient[1])));
                    message = "All applicable classes were moved to an earlier time slot"; 
                }

                if(action.equals("quit")){
                    try {
                        //If quit command received, return the keyword and close the connection
                      out.println("TERMINATE");
                      link.close();				    //Step 5.
                  }
                 catch(IOException e)
                 {
                      System.out.println("Unable to disconnect!");
                      System.exit(1);
                 }
                }

                out.println(message);     //Step 4.
            }
          }
          catch(IncorrectActionException e)
          {
              System.out.println(e.getIncorrectActionException());
          } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}