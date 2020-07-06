package com.example.myfit;

import com.example.myfit.Lesson;

public class UserInLesson extends Lesson {
    public String uuidUser;
    public String type;
    public String date;
    public String time;

    public UserInLesson(){
    }
    public UserInLesson(String uuidUser,String time,String date){
        this.uuidUser = uuidUser;
        this.type = type;
        switch (type){
            case "0": //חד"כ
                maxUserInLesson = "10";
                break;
            case "1"://בריכה
                maxUserInLesson = "15";
                break;
            case "2"://סטודיו
                maxUserInLesson = "20";
                break;
        }
        this.date=date;
        this.time = time;
    }
}
