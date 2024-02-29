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

  public static void main(String[] args) {
    System.out.println("Opening port...\n");
    classes = new ArrayList();
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
          default:
              action = null;
              throw new IncorrectActionException();
      }
      
      //Depending on the action coming from the client, do the thing
      if(action == "consult"){
          
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
      }
      
      //out.println("Response from Server (Capitalized Message): " + message.toUpperCase());     //Step 4.
     }
    catch(IOException e)
    {
        e.printStackTrace();
    }
    catch(IncorrectActionException e)
    {
        System.out.println(e.getIncorrectActionException());
    }
    finally 
    {
       try {
	    System.out.println("\n* Closing connection... *");
            link.close();				    //Step 5.
	}
       catch(IOException e)
       {
            System.out.println("Unable to disconnect!");
	    System.exit(1);
       }
    }
  } // finish run method 
} // finish the class
