package com.app.shop.mylibrary.beans;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class UserBean extends DataSupport implements Serializable {

    private int id;
    private String user_id;
    private String user_no;
    private String name;
    private int photo;
    private String sex;
    private String age;
    private String grade;
    private String mobile;
    private String password;
    private String class_teach;
    private String class_choice;
    private int type;
    private int isSelect;
    private int time_total;
    private String data_lisan;
    private String rigster_time;

    public String getRigster_time() {
        return rigster_time;
    }

    public void setRigster_time(String rigster_time) {
        this.rigster_time = rigster_time;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public int getTime_total() {
        return time_total;
    }

    public void setTime_total(int time_total) {
        this.time_total = time_total;
    }

    public String getData_lisan() {
        return data_lisan;
    }

    public void setData_lisan(String data_lisan) {
        this.data_lisan = data_lisan;
    }

    public int getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(int isSelect) {
        this.isSelect = isSelect;
    }

    public String getClass_choice() {
        return class_choice;
    }

    public void setClass_choice(String class_choice) {
        this.class_choice = class_choice;
    }

    public String getClass_teach() {
        return class_teach;
    }

    public void setClass_teach(String class_teach) {
        this.class_teach = class_teach;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
