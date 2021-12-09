package com.example.demo.dto;

import org.springframework.stereotype.Component;

public class CompanyRequest {
    private String name;

    public CompanyRequest(String name) {
        this.name = name;
    }
    public CompanyRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
