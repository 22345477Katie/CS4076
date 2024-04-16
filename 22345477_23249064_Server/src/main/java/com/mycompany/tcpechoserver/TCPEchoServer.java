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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
  private static final Lock lock = new ReentrantLock();

  public static void main(String[] args) {
        System.out.println("Opening port...\n");
        classes = new ArrayList<>();
        moduleCodes = new ArrayList<>();
        try {
            servSock = new ServerSocket(PORT); // Step 1.
            while (true) { // This loop waits for and handles new connections continuously.
                try {
                    Socket link = servSock.accept(); // Step 2. Accept a new client connection.
                    // New thread for each client
                    ServerThread resource = new ServerThread(link, clientConnections);
                    Thread serverThread = new Thread(resource);
                    serverThread.start();
                    
                    clientConnections++;
                } catch(IOException e) {
                    System.out.println("Error accepting connection: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch(IOException e) {
            System.out.println("Unable to attach to port!");
            System.exit(1);
        }
    }

    public static ArrayList<Classes> getClasses() {
        return classes;
    }

    public static void setClasses(ArrayList<Classes> classes) {
        TCPEchoServer.classes = classes;
    }

    public static ArrayList<String> getModuleCodes() {
        return moduleCodes;
    }

    public static void setModuleCodes(ArrayList<String> moduleCodes) {
        TCPEchoServer.moduleCodes = moduleCodes;
    }
    
    public static void lockData(){
        System.out.println("Locking");
        lock.lock();
    }
    
    public static void unlockData(){
        System.out.println("Unlocking");
        lock.unlock();
    }
}