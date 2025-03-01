/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

/**
 *
 * @author traug
 */
public class Classes {
    
    String courseCode;
    String classCode;
    int day;
    int startingTime;
    int endingTime;
    String room;

    public Classes() {
    }

    public Classes(String courseCode, String classCode, int day, int startingTime, int endingTime, String room) {
        this.courseCode = courseCode;
        this.classCode = classCode;
        this.day = day;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.room = room;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
    
    public String getCourseCode(){
        return courseCode;
    }
    public void setCourseCode(String courseCode){
        this.courseCode = courseCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }
    
    public int getDay(){
        return day;
    }
    
    public void setDay (int day){
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