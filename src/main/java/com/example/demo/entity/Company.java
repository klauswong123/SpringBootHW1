package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.context.annotation.ScopeMetadata;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Company {
    private String id;
    private String name;

    public Company(){

    };

    public Company(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
