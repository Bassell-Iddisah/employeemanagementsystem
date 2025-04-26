package com.example.employeemanagementsystem;

import com.example.employeemanagementsystem.Exceptions.EmployeeNotFoundException;
import com.example.employeemanagementsystem.Exceptions.InvalidDepartmentException;
import com.example.employeemanagementsystem.Exceptions.InvalidSalaryException;
import com.example.employeemanagementsystem.model.Employee;
import com.example.employeemanagementsystem.repository.EmployeeDatabase;
import com.example.employeemanagementsystem.service.EmployeePerformanceComparator;
import com.example.employeemanagementsystem.service.EmployeeSalaryComparator;

import java.util.*;

public class EmployeeManagementApp {

    public static void main(String[] args) {

        EmployeeDatabase<String> db = new EmployeeDatabase<>();

        try {
            // Add sample employees with validation
            addEmployeeSafely(db, new Employee<>(UUID.randomUUID().toString(), "Alice Johnson", "HR", 55000, 4.6, 5, true));
            addEmployeeSafely(db, new Employee<>(UUID.randomUUID().toString(), "Bob Smith", "IT", 70000, 4.2, 8, true));
            addEmployeeSafely(db, new Employee<>(UUID.randomUUID().toString(), "Carol White", "Finance", 64000, 4.8, 4, true));
            addEmployeeSafely(db, new Employee<>(UUID.randomUUID().toString(), "David Brown", "IT", 85000, 3.9, 10, true));
            addEmployeeSafely(db, new Employee<>(UUID.randomUUID().toString(), "Eva Green", "HR", 53000, 4.9, 2, true));

            // Print all employees
            System.out.println("All Employees:\n");
            db.getAllEmployees().forEach(System.out::println);

            // Search by department
            System.out.println("IT Department:\n");
            db.findByDepartment("IT").forEach(System.out::println);

            // High performers
            System.out.println("High Performers (rating >= 4.5):\n");
            db.highPerformers(4.5).forEach(System.out::println);

            // Sort by salary
            List<Employee<String>> sortedBySalary = db.getAllEmployees();
            boolean sameType = sortedBySalary.get(1).getClass() == sortedBySalary.get(0).getClass();
            sortedBySalary.sort(new EmployeeSalaryComparator<>());
            System.out.println("Employees Sorted by Salary:\n");
            sortedBySalary.forEach(System.out::println);

            // Sort by performance
            List<Employee<String>> sortedByPerformance = db.getAllEmployees();
            sortedByPerformance.sort(new EmployeePerformanceComparator<>());
            System.out.println("Employees Sorted by Performance:\n");
            sortedByPerformance.forEach(System.out::println);

            // Top 5 highest paid
            System.out.println("Top 5 Highest Paid:\n");
            db.top5HighestPaid().forEach(System.out::println);

            // Raise salaries
            db.giveRaise(4.5, 1000);
            System.out.println("After Giving Raise (to rating >= 4.5):\n");
            db.getAllEmployees().forEach(System.out::println);

            // Average salary by department
            double avgIT = db.averageSalaryByDepartment("IT");
            System.out.println("Average Salary in IT: $" + avgIT + "\n");

            // Manual iteration
            System.out.println("Traversing using Iterator:");
            Iterator<Employee<String>> it = db.getEmployeeIterator();
            while (it.hasNext()) {
                System.out.println(it.next());
            }

        } catch (InvalidSalaryException | InvalidDepartmentException e) {
            // Generic exception catch block for any unhandled exceptions
            System.err.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            System.out.println("\nApplication execution completed.");
        }
    }

    // Helper method to validate and add employee safely
    private static void addEmployeeSafely(EmployeeDatabase<String> db, Employee<String> employee) throws InvalidSalaryException, InvalidDepartmentException {

        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty.");
        }
        if (employee.getSalary() <= 0) {
            throw new InvalidSalaryException("Salary cannot be negative for " + employee.getName());
        }
        if (employee.getDepartment() == null) {
            throw new InvalidDepartmentException("Employee must belong to a department");
        }
        db.addEmployee(employee);
    }
}
