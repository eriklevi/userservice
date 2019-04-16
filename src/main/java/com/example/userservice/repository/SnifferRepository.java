package com.example.userservice.repository;

import com.example.userservice.entity.Sniffer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnifferRepository extends MongoRepository<Sniffer, String> {
}
