package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.collection.NaverITNews;

public interface NaverItNewsRepository extends MongoRepository<NaverITNews, String>{

}
