module com.example.employeemanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;


    opens com.example.employeemanagementsystem to javafx.fxml;
    exports com.example.employeemanagementsystem;
    exports com.example.employeemanagementsystem.model;
    opens com.example.employeemanagementsystem.model to javafx.fxml;
    exports com.example.employeemanagementsystem.repository;
    opens com.example.employeemanagementsystem.repository to javafx.fxml;
    exports com.example.employeemanagementsystem.service;
    opens com.example.employeemanagementsystem.service to javafx.fxml;
    exports com.example.employeemanagementsystem.controller;
    opens com.example.employeemanagementsystem.controller to javafx.fxml;
}