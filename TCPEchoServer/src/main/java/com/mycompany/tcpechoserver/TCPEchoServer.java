/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tcpechoserver;
import data.Classes;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author razi
 */
public class TCPEchoServer { 
  private static ServerSocket servSock;
  private static final int PORT = 1234;
  private static int clientConnections = 0;
  private static ArrayList<Classes> classes;
  private static ArrayList<String> moduleCodes;

  public static void main(String[] args) {
    System.out.println("Opening port...\n");
    classes = new ArrayList();
    moduleCodes = new ArrayList();
    try 
    {
        servSock = new ServerSocket(PORT);      //Step 1.
    }
    catch(IOException e) 
    {
         System.out.println("Unable to attach to port!");
         System.exit(1);
    }
    
    do 
    {
         run();
    }while (true);

  }
  
  private static void run()
  {
    Socket link = null;                        //Step 2.
    try 
    {
      link = servSock.accept();               //Step 2.
      clientConnections++;
      BufferedReader in = new BufferedReader( new InputStreamReader(link.getInputStream())); //Step 3.
      PrintWriter out = new PrintWriter(link.getOutputStream(),true); //Step 3.
      String message = "";
      
      String messageClient = in.readLine();         //Step 4.
      System.out.println("Message received from client: " + clientConnections + "  "+ messageClient);
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
              action = "quit";
              break;
          default:
              action = null;
              throw new IncorrectActionException("No action selected.");
      }
     
      
      //Depending on the action coming from the client, do the thing
      if(action.equals("add")){
        try{
              int day;
            switch (actionClient[2]) {
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
          
            Classes scheduledClass = new Classes(actionClient[1], day, Integer.parseInt(actionClient[3]), Integer.parseInt(actionClient[4]), actionClient[5]);

            if(Integer.parseInt(actionClient[4])<=Integer.parseInt(actionClient[3])){
                throw new IncorrectActionException("The class end time must be after the start time. Class not scheduled.");
            }
            
            if(classes.isEmpty()==false){
                if(moduleCodes.contains(scheduledClass.getClassCode())==false && moduleCodes.size()>=5){
                    throw new IncorrectActionException("A schedule cannot contain more than 5 modules per semester.");
                }
                for (int i = 0; i<classes.size(); i++){
                    if(scheduledClass.getDay()==classes.get(i).getDay()){
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
                classes.add(scheduledClass);
                message = "The class was added to the schedule successfully."; 
            }else{
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
              int day;
            switch (actionClient[2]) {
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
            Classes scheduledClass = new Classes(actionClient[1], day, Integer.parseInt(actionClient[3]), Integer.parseInt(actionClient[4]), actionClient[5]);
            if(classes.isEmpty()){
                throw new IncorrectActionException("There are currently no scheduled classes to be removed");
            }
            for(int i = 0; i<classes.size(); i++){
                if(scheduledClass.getClassCode().equals(classes.get(i).getClassCode())
                        &&scheduledClass.getDay()==classes.get(i).getDay()
                        &&scheduledClass.getStartingTime()==classes.get(i).getStartingTime()
                        &&scheduledClass.getEndingTime()==classes.get(i).getEndingTime()
                        &&scheduledClass.getRoom().equals(classes.get(i).getRoom())){
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
      
      /*if(action == "consult"){
          
          //TEST
          Classes classTest = new Classes("CS4076", Classes.DaysOfTheWeek.Monday, 9, 11, "CS2044");
          classes.add(classTest);
          //
          
          if(classes.size() == 0){
              out.println("The schedule is empty ! Add a class first.");
          } else if (actionClient.length < 1){
              out.println("No class code found");
          } else {
            String classCode = actionClient[1];
            String schedule = "CLASS";
            for(int i = 0; i < classes.size(); i ++){
                if(classes.get(i).getClassCode().equals(classCode)){
                    schedule += "-" + classes.get(i).getClassCode() + "/"
                            + classes.get(i).getRoom() + "/" +
                            classes.get(i).getDay().toString() + "/" +
                            classes.get(i).getStartingTime() + "/" +
                            classes.get(i).getEndingTime();
                }
                System.out.println("Class " + classes.get(i).getClassCode() + 
                        " in room " + classes.get(i).getRoom() + " starting at " +
                        classes.get(i).getStartingTime() + " until " + classes.get(i).getEndingTime()
                        + " on " + classes.get(i).getDay().toString());
            }
            if(schedule.equals("CLASS")){
                schedule = "No class found with this code";
            }
            out.println(schedule);
          }
      }*/
      
      if(action.equals("quit")){
          try {
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
    catch(IOException e)
    {
        e.printStackTrace();
    }
    catch(IncorrectActionException e)
    {
        System.out.println(e.getIncorrectActionException());
    }
    
  } // finish run method 
} // finish the class