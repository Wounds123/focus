package com.app.problem_collect;

public class HeroBean {
    //private int icon;
    private String name;
    private String quri="nothing";
    private String auri="nothing";

    public HeroBean() {
    }



    public HeroBean(String name,String quri,String auri) {
        //this.icon = icon;
        this.auri=auri;
        this.quri=quri;
        this.name = name;
    }

    public HeroBean(String name,String quri) {
        this.quri=quri;
       // this.icon = icon;
        this.name = name;
    }

    public HeroBean(String name){
        this.name=name;
    }



   // public void setIcon(int icon) { this.icon = icon; }
    public void setName(String name) { this.name = name; }
    public void setAuri(String auri) { this.auri=auri; }
    public void setQuri(String quri){this.quri=quri;}

    public String getAuri(){
        return auri;
    }
    public String getQuri(){
        return quri;
    }
    //public int getIcon() { return icon; }
    public String getName() { return name; }




}