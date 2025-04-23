package com.example.employeemanagementsystem;

import com.example.employeemanagementsystem.model.Employee;
import com.example.employeemanagementsystem.repository.EmployeeDatabase;
import com.example.employeemanagementsystem.service.EmployeePerformanceComparator;
import com.example.employeemanagementsystem.service.EmployeeSalaryComparator;

import java.util.*;

public class EmployeeManagementApp {

    public static void main(String[] args) {

        EmployeeDatabase<String> db = new EmployeeDatabase<>();
        String uniqueID = UUID.randomUUID().toString();

        // Add sample employees
        db.addEmployee(new Employee<>(UUID.randomUUID().toString(), "Alice Johnson", "HR", 55000, 4.6, 5, true));
        db.addEmployee(new Employee<>(UUID.randomUUID().toString(), "Bob Smith", "IT", 70000, 4.2, 8, true));
        db.addEmployee(new Employee<>(UUID.randomUUID().toString(), "Carol White", "Finance", 64000, 4.8, 4, true));
        db.addEmployee(new Employee<>(UUID.randomUUID().toString(), "David Brown", "IT", 85000, 3.9, 10, true));
        db.addEmployee(new Employee<>(UUID.randomUUID().toString(), "Eva Green", "HR", 53000, 4.9, 2, true));

        // Print all employees
        System.out.println("All Employees:");
        System.out.println();

        db.getAllEmployees().forEach(System.out::println);

        // Search by department
        System.out.println("IT Department:");
        System.out.println();

        db.findByDepartment("IT").forEach(System.out::println);

        // High performers
        System.out.println("High Performers (rating >= 4.5):");
        System.out.println();

        db.highPerformers(4.5).forEach(System.out::println);

        // Sort by salary
        List<Employee<String>> sortedBySalary = db.getAllEmployees();
        sortedBySalary.sort(new EmployeeSalaryComparator<>());
        System.out.println("Employees Sorted by Salary:");
        System.out.println();

        sortedBySalary.forEach(System.out::println);

        // Sort by performance
        List<Employee<String>> sortedByPerformance = db.getAllEmployees();
        sortedByPerformance.sort(new EmployeePerformanceComparator<>());
        System.out.println("Employees Sorted by Performance:");
        System.out.println();

        sortedByPerformance.forEach(System.out::println);

        // Top 5 highest paid
        System.out.println("Top 5 Highest Paid:");
        System.out.println();
        db.top5HighestPaid().forEach(System.out::println);

        // Raise salaries
        db.giveRaise(4.5, 1000);
        System.out.println("After Giving Raise (to rating >= 4.5):");
        System.out.println();
        db.getAllEmployees().forEach(System.out::println);

        // Average salary by department
        double avgIT = db.averageSalaryByDepartment("IT");
        System.out.println("Average Salary in IT: $" + avgIT);
        System.out.println();

        // Manual iteration
        System.out.println("Traversing using Iterator:");
        Iterator<Employee<String>> it = db.getEmployeeIterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

//        public String report() {
//
//        }
    }
}
