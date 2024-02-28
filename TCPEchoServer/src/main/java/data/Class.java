/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

/**
 *
 * @author traug
 */
public class Class {
    public enum DaysOfTheWeek{
        Monday,
        Tuesday,
        Wednesday,
        Thursday,
        Friday
    }
    
    String classCode;
    DaysOfTheWeek day;
    int startingTime;
    int endingTime;

    public Class() {
    }

    public Class(String classCode, DaysOfTheWeek day, int startingTime, int endingTime) {
        this.classCode = classCode;
        this.day = day;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public DaysOfTheWeek getDay() {
        return day;
    }

    public void setDay(DaysOfTheWeek day) {
        this.day = day;
    }

    public int getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(int startingTime) {
        this.startingTime = startingTime;
    }

    public int getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(int endingTime) {
        this.endingTime = endingTime;
    }
    
    
    
    
}
