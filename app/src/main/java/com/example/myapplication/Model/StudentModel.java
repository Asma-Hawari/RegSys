package com.example.myapplication.Model;

import java.util.ArrayList;

public class StudentModel {
    private int id;
    private String first_name;
    private String last_name;
    private String reg_year;
    private String gender;
    private String mobile;
    private String address;
    //private ArrayList enrollment;

    public StudentModel(){
        this.first_name = "";
        this.last_name = "";
        this.gender = "" ;
        this.mobile = "";
        this.address = "";
    }

    public StudentModel(int id, String first_name, String last_name, String gender, String mobile, String address) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        //this.reg_year = reg_year;
        this.gender = gender ;
        this.mobile = mobile;
        this.address = address;
    }
//    public StudentModel(){
//        this.id = 0;
//        this.first_name = "Asma";
//        this.last_name = "Hawari";
//        this.reg_year = 2020;
//        this.gender = "female";
//        this.mobile = "0988898989";
//        this.address = "AL Muhajreen";
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getReg_year() {
        return reg_year;
    }

    public void setReg_year(String reg_year) {
        this.reg_year = reg_year;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /*public ArrayList getEnrollment() {
        return enrollment;
    }*/

    /*public void setEnrollment(ArrayList enrollment) {
        this.enrollment = enrollment;
    }*/
}
