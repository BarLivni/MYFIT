package com.example.myfit;


public class Lesson {
    public String id;
    public String type;
    public String hour;
    public String date; // year/month/day
    public User usersInLesson[];
    public String maxUserInLesson;

    public Lesson(){
    }
    public Lesson(String id,String type, String hour,String date){
        this.id = id;
        this.type = type;
        this.hour=hour;
        this.date=date;
        this.maxUserInLesson = "10";
    }
    public Lesson(String id,String type, String hour,String date,User userInLesson[]){
        this.id = id;
        this.type = type;
        this.hour=hour;
        this.date=date;
        this.usersInLesson=userInLesson;
        this.maxUserInLesson = "10";
    }


}
