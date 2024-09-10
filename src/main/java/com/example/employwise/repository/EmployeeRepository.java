package com.example.employwise.repository;

import com.example.employwise.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface EmployeeRepository extends MongoRepository<Employee, UUID>, PagingAndSortingRepository<Employee, UUID> {
}