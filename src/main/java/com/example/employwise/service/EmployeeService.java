package com.example.employwise.service;

import com.example.employwise.model.Employee;
import com.example.employwise.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.mail.MailAuthenticationException;

import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmailService emailService;

    public Employee addEmployee(Employee employee) {
        employee.setId(UUID.randomUUID());
        Employee savedEmployee = employeeRepository.save(employee);

        Employee level1Manager = findLevel1Manager(savedEmployee);
        if (level1Manager != null) {
            String emailBody = String.format("%s will now work under you. Mobile number is %s and email is %s",
                savedEmployee.getEmployeeName(), savedEmployee.getPhoneNumber(), savedEmployee.getEmail());
            try {
                emailService.sendEmail(level1Manager.getEmail(), "New Employee Added", emailBody);
            } catch (MailAuthenticationException e) {
                System.err.println("Failed to send email notification: " + e.getMessage());
            }
        }

        return savedEmployee;
    }

    private Employee findLevel1Manager(Employee employee) {
        Employee currentManager = employee;
        while (currentManager != null && currentManager.getReportsTo() != null) {
            currentManager = employeeRepository.findById(currentManager.getReportsTo()).orElse(null);
        }
        return currentManager;
    }

    public Page<Employee> getAllEmployees(int page, int size, String[] sort) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sort[1].equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));
        return employeeRepository.findAll(pageable);
    }

    public Employee getEmployeeById(UUID id) {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public Employee updateEmployee(UUID id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setEmployeeName(employeeDetails.getEmployeeName());
        employee.setPhoneNumber(employeeDetails.getPhoneNumber());
        employee.setEmail(employeeDetails.getEmail());
        employee.setReportsTo(employeeDetails.getReportsTo());
        employee.setProfileImage(employeeDetails.getProfileImage());

        return employeeRepository.save(employee);
    }

    public void deleteEmployee(UUID id) {
        employeeRepository.deleteById(id);
    }

    public Employee getNthLevelManager(UUID employeeId, int level) {
        Employee employee = getEmployeeById(employeeId);
        for (int i = 0; i < level && employee != null; i++) {
            if (employee.getReportsTo() == null) {
                return null;
            }
            employee = getEmployeeById(employee.getReportsTo());
        }
        return employee;
    }
}