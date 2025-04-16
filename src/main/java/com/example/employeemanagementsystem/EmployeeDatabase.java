package com.example.employeemanagementsystem;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class EmployeeDatabase<T> {

    private Map<T, Employee<T>> employeeMap;

    public EmployeeDatabase() {
        this.employeeMap = new HashMap<>();
    }

    // CREATE
    public void addEmployee(Employee<T> employee) {
        employeeMap.put(employee.getEmployeeId(), employee);
    }

    // READ ALL
    public List<Employee<T>> getAllEmployees() {
        return new ArrayList<>(employeeMap.values());
    }

    // UPDATE: Generic field update
    public void updateEmployeeDetails(T employeeId, String field, Object newValue) {
        Employee<T> emp = employeeMap.get(employeeId);
        if (emp == null) return;

        switch (field.toLowerCase()) {
            case "name" -> emp.setName((String) newValue);
            case "department" -> emp.setDepartment((String) newValue);
            case "salary" -> emp.setSalary((Double) newValue);
            case "performancerating" -> emp.setPerformanceRating((Double) newValue);
            case "yearsofexperience" -> emp.setYearsOfExperience((Integer) newValue);
            case "isactive" -> emp.setActive((Boolean) newValue);
        }
    }

    // DELETE
    public void removeEmployee(T employeeId) {
        employeeMap.remove(employeeId);
    }

    // SEARCH by department
    public List<Employee<T>> findByDepartment(String department) {
        return employeeMap.values().stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    // SEARCH by name part
    public List<Employee<T>> searchByName(String namePart) {
        return employeeMap.values().stream()
                .filter(e -> e.getName().toLowerCase().contains(namePart.toLowerCase()))
                .collect(Collectors.toList());
    }

    // FILTER by performance rating
    public List<Employee<T>> highPerformers(double minRating) {
        return employeeMap.values().stream()
                .filter(e -> e.getPerformanceRating() >= minRating)
                .collect(Collectors.toList());
    }

    // FILTER by salary range
    public List<Employee<T>> employeesInSalaryRange(double minSalary, double maxSalary) {
        return employeeMap.values().stream()
                .filter(e -> e.getSalary() >= minSalary && e.getSalary() <= maxSalary)
                .collect(Collectors.toList());
    }

    // SALARY RAISE
    public void giveRaise(double thresholdRating, double raiseAmount) {
        employeeMap.values().stream()
                .filter(e -> e.getPerformanceRating() >= thresholdRating)
                .forEach(e -> e.setSalary(e.getSalary() + raiseAmount));
    }

    // TOP 5 by salary
    public List<Employee<T>> top5HighestPaid() {
        return employeeMap.values().stream()
                .sorted(Comparator.comparingDouble(Employee<T>::getSalary).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    // AVERAGE salary by department
    public double averageSalaryByDepartment(String department) {
        return employeeMap.values().stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
    }

    // ITERATOR for manual traversal
    public Iterator<Employee<T>> getEmployeeIterator() {
        return employeeMap.values().iterator();
    }
}
