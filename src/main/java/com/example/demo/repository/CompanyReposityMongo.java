package com.example.demo.repository;

import com.example.demo.entity.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyReposityMongo extends MongoRepository<Company, String> {

}