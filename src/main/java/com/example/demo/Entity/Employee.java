package com.example.demo.Entity;


import java.util.Arrays;

public class Employee {
    private String name;
    private Integer id;
    private Integer age;
    private Integer salary;
    private String gender;

    private Integer companyID;
    public Employee(String name, Integer id, Integer age, Integer salary, String gender, Integer... companyID) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.salary = salary;
        this.gender = gender;
        if(companyID.length!=0){
            this.companyID=companyID[0];
        }
        else{
            this.companyID=1;
        }
    }

    public Integer getCompanyID() {
        return this.companyID;
    }

    public void setCompanyID(Integer companyID) {
        this.companyID = companyID;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getSalary() {
        return salary;
    }

    public String getGender() {
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}
