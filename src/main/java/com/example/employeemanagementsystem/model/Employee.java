package com.example.employeemanagementsystem.model;

import javafx.beans.property.*;

public class Employee<T> implements Comparable<Employee<T>> {

    private T employeeId;
    private final StringProperty employeeIdProperty;

    private final StringProperty name;
    private final StringProperty department;
    private final DoubleProperty salary;
    private final DoubleProperty performanceRating;
    private final IntegerProperty yearsOfExperience;
    private final BooleanProperty isActive;

    public Employee(T employeeId, String name, String department, double salary,
                    double performanceRating, int yearsOfExperience, boolean isActive) {
        this.employeeId = employeeId;
        this.employeeIdProperty = new SimpleStringProperty(employeeId.toString());

        this.name = new SimpleStringProperty(name);
        this.department = new SimpleStringProperty(department);
        this.salary = new SimpleDoubleProperty(salary);
        this.performanceRating = new SimpleDoubleProperty(performanceRating);
        this.yearsOfExperience = new SimpleIntegerProperty(yearsOfExperience);
        this.isActive = new SimpleBooleanProperty(isActive);
    }

    // JavaFX property accessors (for TreeTableView bindings)
    public StringProperty employeeIdProperty() { return employeeIdProperty; }
    public StringProperty nameProperty() { return name; }
    public StringProperty departmentProperty() { return department; }
    public DoubleProperty salaryProperty() { return salary; }
    public DoubleProperty performanceRatingProperty() { return performanceRating; }
    public IntegerProperty yearsOfExperienceProperty() { return yearsOfExperience; }
    public BooleanProperty isActiveProperty() { return isActive; }

    // Traditional getters/setters
    public T getEmployeeId() { return employeeId; }
    public void setEmployeeId(T employeeId) {
        this.employeeId = employeeId;
        this.employeeIdProperty.set(employeeId.toString());
    }

    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }

    public String getDepartment() { return department.get(); }
    public void setDepartment(String department) { this.department.set(department); }

    public double getSalary() { return salary.get(); }
    public void setSalary(double salary) { this.salary.set(salary); }

    public double getPerformanceRating() { return performanceRating.get(); }
    public void setPerformanceRating(double performanceRating) { this.performanceRating.set(performanceRating); }

    public int getYearsOfExperience() { return yearsOfExperience.get(); }
    public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience.set(yearsOfExperience); }

    public boolean isActive() { return isActive.get(); }
    public void setActive(boolean active) { this.isActive.set(active); }

    @Override
    public int compareTo(Employee<T> other) {
        return Integer.compare(other.getYearsOfExperience(), this.getYearsOfExperience());
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Dept: %s | Salary: %.2f | Rating: %.1f | Exp: %d | Active: %b",
                employeeId, getName(), getDepartment(), getSalary(), getPerformanceRating(), getYearsOfExperience(), isActive());
    }
}
