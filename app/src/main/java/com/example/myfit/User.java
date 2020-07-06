package com.example.myfit;

public class User {
    public String name;
    public String phone;
    public Lesson myLessons[];

    public User(){
    }
    public User(String name,String phone) {
        this.name = name;
        this.phone = phone;
    }

    public User(String name,String phone,Lesson myLessond[]){
        this.name=name;
        this.phone=phone;
        this.myLessons=myLessond;

    }
}
