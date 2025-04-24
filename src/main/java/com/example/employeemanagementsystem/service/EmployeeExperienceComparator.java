package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.model.Employee;

import java.util.Comparator;

public class EmployeeExperienceComparator<T> implements Comparator<Employee<T>> {

    @Override
    public int compare(Employee<T> e1, Employee<T> e2) {
        return Double.compare(e2.getYearsOfExperience(), e1.getYearsOfExperience()); // descending
    }
}
