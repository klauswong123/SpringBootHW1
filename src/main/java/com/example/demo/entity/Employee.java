package com.example.demo.entity;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.Id;

@Document
public class Employee {
    @MongoId(FieldType.OBJECT_ID)
    @Id
    private String id;
    private String name;
    private Integer age;
    private Integer salary;
    private String gender;

    private String companyID;
    public Employee(String name, String id, Integer age, Integer salary, String gender, String companyID) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.salary = salary;
        this.gender = gender;
        if(companyID!=null){
            this.companyID=companyID;
        }
    }
    public Employee(){

    }

    public String getCompanyID() {
        return this.companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getName() {
        return name;
    }

    public String getId() {
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

    public void setId(String id) {
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
