/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tcpechoserver;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;
import data.Classes;

/**
 *
 * @author katie
 */

import java.util.concurrent.ForkJoinTask;

public class EarlyThread{
    public static class ThreadCreation extends ForkJoinTask<ArrayList<Classes>>{
        ArrayList<Classes> classes = new ArrayList<>();
        String courseCode;
        int day = 1;
        ArrayList<Classes> result = new ArrayList<>();
        
        public ThreadCreation(ArrayList<Classes> classes, String courseCode){
            this.classes = classes;
            this.courseCode = courseCode;
        }
        
        @Override
        protected boolean exec(){
            //I understand this isn't the most useful use of ForkJoin but it is quite honestly one of the only ways we could figure out to include 
                //ForkJoin for this specific scenario
            ForkJoinTask<ArrayList<Classes>> day1 = new Computation(classes, courseCode, day).fork();
            ForkJoinTask<ArrayList<Classes>> day2 = new Computation(classes, courseCode, day+1).fork();
            ForkJoinTask<ArrayList<Classes>> day3 = new Computation(classes, courseCode, day+2).fork();
            ForkJoinTask<ArrayList<Classes>> day4 = new Computation(classes, courseCode, day+3).fork();
            ForkJoinTask<ArrayList<Classes>> day5 = new Computation(classes, courseCode, day+4).fork();
            result.addAll(day1.join());
            result.addAll(day2.join()); 
            result.addAll(day3.join());
            result.addAll(day4.join());
            result.addAll(day5.join());
            
            setRawResult(result);
            
            return true;
        }
        
         @Override
        public ArrayList<Classes> getRawResult() {
          return this.result;
        }

        @Override
        protected void setRawResult(ArrayList<Classes> value) {
          this.result = value;
        }
    }
    
    public static class Computation extends ForkJoinTask<ArrayList<Classes>>{
        
        public ArrayList<Classes> classes;
        String courseCode;
        int day;
        ArrayList<Classes> result = new ArrayList<>();
        
        public Computation(ArrayList<Classes> classes, String courseCode, int day){
            this.classes=classes;
            this.courseCode = courseCode;
            this.day = day;
        }
        
        void merge(ArrayList<Classes> classArrL, int start, int mid, int end){
            int n1 = mid - start + 1;
            int n2 = end - mid;

            ArrayList<Classes> t1 = new ArrayList<>();
            ArrayList<Classes> t2 = new ArrayList<>();

            for (int i = 0; i < n1; i++){
                t1.add(classArrL.get(start + i));
            }
            for (int j = 0; j < n2; j++){
                t2.add(classArrL.get(mid + 1 + j));
            }

            int i = 0, j = 0;
            int k = start;
            
            while (i < n1 && j < n2) {
                if (t1.get(i).getStartingTime() <= t2.get(j).getStartingTime()) {
                    classArrL.set(k, t1.get(i));
                    i++;
                }else{
                    classArrL.set(k, t2.get(j));
                    j++;
                }
                k++;
            }

            while (i < n1) {
                classArrL.set(k, t1.get(i));
                i++;
                k++;
            }

            while (j < n2) {
                classArrL.set(k, t2.get(j));
                j++;
                k++;
            }
        }   

        void sort(ArrayList<Classes> classArrL, int start, int end)
        {
            if (start< end) {

                int mid = start + (end - start) / 2;

                sort(classArrL, start, mid);
                sort(classArrL, mid + 1, end);

                merge(classArrL, start, mid, end);
            }
        }
        
        
        @Override
        protected boolean exec(){
            ArrayList<Classes> dayClasses = new ArrayList<>();
            if(classes.isEmpty()==false){
                //makes an array of all classes for a specific day (unsorted)
                for (int i = 0; i<classes.size(); i++){
                    if(classes.get(i).getCourseCode().equals(courseCode) && day == classes.get(i).getDay()){
                        dayClasses.add(classes.get(i));
                    }                                          
                }

                //DIVIDE AND CONQUER    
                //mergesort algorithm in order to get all of the classes in order to properly move them forwards
                //methods sort and merge above
                sort(dayClasses, 0, dayClasses.size()-1);

                //check if there is free space above each class in order - if there is, move it forward to the earliest available time
                int hour = 9;
                for(int i = 0; i<dayClasses.size(); i++){
                    if (hour<dayClasses.get(i).getStartingTime()){
                        int adjustment = dayClasses.get(i).getStartingTime()-hour;
                        dayClasses.get(i).setEndingTime(dayClasses.get(i).getEndingTime()-adjustment);
                        dayClasses.get(i).setStartingTime(hour);
                        hour = dayClasses.get(i).getEndingTime();
                    }else{
                        hour = dayClasses.get(i).getEndingTime();
                    }
                }
                setRawResult(dayClasses);
            }else{
                setRawResult(dayClasses);
            }
            
            return true; 
        }
        
        @Override
        public ArrayList<Classes> getRawResult() {
          return this.result;
        }

        @Override
        protected void setRawResult(ArrayList<Classes> value) {
          this.result = value;
        }
    }
}



