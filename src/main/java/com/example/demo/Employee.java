package com.example.demo;


public class Employee {
    private String name;
    private Integer id;
    private Integer age;
    Employee(String name, Integer id, Integer age){
        this.name=name;
        this.id = id;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Integer getAge(){return age;}
    public void setId(Integer id) {
        this.id=id;
    }
    public void setName(String name) {
        this.name=name;
    }
    public void setAge(Integer age){this.age=age;}

    public int getId(Employee employee) {
        return id;
    }
}
