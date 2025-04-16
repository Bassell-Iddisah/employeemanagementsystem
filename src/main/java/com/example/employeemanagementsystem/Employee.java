package com.example.employeemanagementsystem;

public class Employee<T> implements Comparable<Employee<T>> {

    private T employeeId;
    private String name;
    private String department;
    private double salary;
    private double performanceRating;
    private int yearsOfExperience;
    private boolean isActive;

    // Construct the Employee object
    public Employee(T employeeId, String name, String department, double salary,
                    double performanceRating, int yearsOfExperience, boolean isActive) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.performanceRating = performanceRating;
        this.yearsOfExperience = yearsOfExperience;
        this.isActive = isActive;
    }

    // Get an EmployeeID
    public T getEmployeeId() {
        return employeeId;
    }

    // Set an EmployeeID
    public void setEmployeeId(T employeeId) {
        this.employeeId = employeeId;
    }

    // Return employee name
    public String getName() {
        return name;
    }

    // Set employee name
    public void setName(String name) {
        this.name = name;
    }

    // Get employee department
    public String getDepartment() {
        return department;
    }

    // Set employee department
    public void setDepartment(String department) {
        this.department = department;
    }

    // Get employ salary
    public double getSalary() {
        return salary;
    }

    // Set employ salary
    public void setSalary(double salary) {
        this.salary = salary;
    }

    //
    public double getPerformanceRating() {
        return performanceRating;
    }

    public void setPerformanceRating(double performanceRating) {
        this.performanceRating = performanceRating;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // Sorting employees by years of experience
    @Override
    public int compareTo(Employee<T> other) {
        return Integer.compare(other.getYearsOfExperience(), this.getYearsOfExperience());
    }

    // format for a clean output
    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Dept: %s | Salary: %.2f | Rating: %.1f | Exp: %d | Active: %b",
                employeeId, name, department, salary, performanceRating, yearsOfExperience, isActive);
    }
}
