package com.app.lockscreen;

import cn.bmob.v3.BmobObject;

public class User_Name_List extends BmobObject {
    private String User_Name;
    private Integer Lock_Time;
    private String ID;

    public User_Name_List(String a,Integer time){
        this.User_Name=a;
        this.Lock_Time=time;
    }
    public User_Name_List(){

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public Integer getLock_Time() {
        return Lock_Time;
    }

    public void setLock_Time(Integer lock_Time) {
        Lock_Time = lock_Time;
    }
}
