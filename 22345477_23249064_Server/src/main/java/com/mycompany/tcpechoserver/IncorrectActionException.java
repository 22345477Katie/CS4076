/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package com.mycompany.tcpechoserver;

/**
 *
 * @author traug
 */
public class IncorrectActionException extends Exception{
    String message;

    public IncorrectActionException() {
        this.message = "Incorrect action exception";
    }

    public IncorrectActionException(String msg) {
        this.message = msg;
    }
    
    public String getIncorrectActionException(){
        return message;
    }
}