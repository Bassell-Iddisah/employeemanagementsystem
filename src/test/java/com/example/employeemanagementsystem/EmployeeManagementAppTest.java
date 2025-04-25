package com.example.employeemanagementsystem;

import com.example.employeemanagementsystem.Exceptions.InvalidDepartmentException;
import com.example.employeemanagementsystem.Exceptions.InvalidSalaryException;
import com.example.employeemanagementsystem.model.Employee;
import com.example.employeemanagementsystem.EmployeeManagementApp;

import static org.junit.jupiter.api.Assertions.*;

import com.example.employeemanagementsystem.repository.EmployeeDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class EmployeeTest {

    private Employee<String> employee;

    // Initialize new database and add new employee
    @BeforeEach
    void setUp() throws InvalidDepartmentException, InvalidSalaryException {
        EmployeeDatabase<String> db = new EmployeeDatabase<>();
         employee = new Employee<>("EMP123", "Alice", "Finance", 60000, 4.2, 5, true);

    }

    @Test
    void testInitialValues() {
        assertNotNull(employee);
        assertEquals("EMP123", employee.getEmployeeId());
        assertEquals("Alice", employee.getName());
        assertEquals("Finance", employee.getDepartment());
        assertEquals(60000, employee.getSalary());
        assertEquals(4.2, employee.getPerformanceRating());
        assertEquals(5, employee.getYearsOfExperience());
        assertTrue(employee.isActive());
    }

    // Testing getters and setters
    @Test
    void testSettersUpdateValues() {
        employee.setName("Bob");
        employee.setDepartment("HR");
        employee.setSalary(70000);
        employee.setPerformanceRating(4.8);
        employee.setYearsOfExperience(7);
        employee.setActive(false);

        assertEquals("Bob", employee.getName());
        assertEquals("HR", employee.getDepartment());
        assertEquals(70000, employee.getSalary());
        assertEquals(4.8, employee.getPerformanceRating());
        assertEquals(7, employee.getYearsOfExperience());
        assertFalse(employee.isActive());
    }

    //  Test descending order comparison
    @Test
    void testCompareTo() {
        Employee<String> junior = new Employee<>("EMP456", "Charlie", "IT", 50000, 3.5, 2, true);
        assertTrue(employee.compareTo(junior) < 0); // More experience comes first
    }

    // Test output
    @Test
    void testToStringOutput() {
        String result = employee.toString();
        assertTrue(result.contains("Alice"));
        assertTrue(result.contains("Finance"));
        assertTrue(result.contains("60000.00"));
        assertTrue(result.contains("Exp: 5"));
    }

    // Test for not null
    @Test
    void testNotNull() {

    }
}
