package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.model.Employee;
import com.example.employeemanagementsystem.repository.EmployeeDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
//import javafx.scene.control.Al;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HelloController {
    EmployeeDatabase<String> db = new EmployeeDatabase<>();

//    @FXML
//    private Button onHelloButtonClick;
// ----------------------------------------------
    @FXML
    private Label welcomeText;

    @FXML
    private Text employeeWelcomText;

    @FXML
    private TextField employeeID;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private TextField searchFilter;

    @FXML
    private Button sortButton;

    @FXML
    private Button sortComboBox;

    @FXML
    private Button clearTable;

    @FXML
    private TextField employeeFirstname;

    @FXML
    private TextField employeeLastname;

    @FXML
    private ComboBox<String> departmentComboBox;

    @FXML
    private TextField employeeSalary;

    @FXML
    private CheckBox employeeInService;

    @FXML
    private Button deleteEmployeeButton;


    @FXML private TreeTableView<Employee<String>> employeeTable;
    @FXML private TreeTableColumn<Employee<String>, String> treeTableid;
    @FXML private TreeTableColumn<Employee<String>, String> treeTableName;
    @FXML private TreeTableColumn<Employee<String>, String> treeTableDepartment;
    @FXML private TreeTableColumn<Employee<String>, Number> treeTableSalary;
    @FXML private TreeTableColumn<Employee<String>, Number> treeTablePerformance;
    @FXML private TreeTableColumn<Employee<String>, Number> treeTableExperience;
    @FXML private TreeTableColumn<Employee<String>, Boolean> treeTableStatus;




//    ------------------------------------------------------------------------------

    @FXML
    private ComboBox<String> averageSalaryDepartment;

//    ------------------------------------------------------------------------------

    @FXML
    private void initialize() {
        departmentComboBox.getItems().addAll("HR", "IT", "Finance");
        averageSalaryDepartment.getItems().addAll("HR", "IT", "Finance");
        filterComboBox.getItems().addAll("Departments", "Name", "Performance", "Salary");


        // Set cell value factories (bind to observable properties)
        treeTableid.setCellValueFactory(param -> param.getValue().getValue().employeeIdProperty());
        treeTableName.setCellValueFactory(param -> param.getValue().getValue().nameProperty());
        treeTableDepartment.setCellValueFactory(param -> param.getValue().getValue().departmentProperty());
        treeTableSalary.setCellValueFactory(param -> param.getValue().getValue().salaryProperty());
        treeTablePerformance.setCellValueFactory(param -> param.getValue().getValue().performanceRatingProperty());
        treeTableExperience.setCellValueFactory(param -> param.getValue().getValue().yearsOfExperienceProperty());
        treeTableStatus.setCellValueFactory(param -> param.getValue().getValue().isActiveProperty());
    }

    @FXML
    void sortComboBoxHandler(ActionEvent event) {
        // Create root item (invisible root)
        TreeItem<Employee<String>> root = new TreeItem<>(new Employee<>("root", "", "", 0, 0, 0, false));
        root.setExpanded(true);

        ArrayList<Employee> employees = new ArrayList<>(db.getAllEmployees());
        for (Employee employee: employees) {
            root.getChildren().add(new TreeItem<>(new Employee<>(employee.getEmployeeId().toString(), employee.getName(), employee.getDepartment(), employee.getSalary(), employee.getPerformanceRating(), employee.getYearsOfExperience(), true)));
        }

        employeeTable.setRoot(root);
        employeeTable.setShowRoot(false); // Hide root from view
    }

    @FXML
    void filterComboBoxHandler(ActionEvent event) {

    }

    @FXML
    void sortInputHandler(ActionEvent event) {

    }

    @FXML
    void searchButtonHandler(ActionEvent event) {
        String searchBy = filterComboBox.getValue();
        String query = searchFilter.getText();

        System.out.println(searchBy);
        System.out.println();
        System.out.println(query);


    }

    @FXML
    void honorificMenuButtonHandler(ActionEvent event) {

    }

    @FXML
    void departmentComboBoxHandler(ActionEvent event) {

    }

    @FXML
    void employeeInServiceHandler(ActionEvent event) {

    }

    @FXML
    void addEmployeeButtonHandler(ActionEvent event) {
        try {
            String firstName = employeeFirstname.getText();
            String lastName = employeeLastname.getText();
            String selectedDepartment = departmentComboBox.getSelectionModel().getSelectedItem().toString();
            Double salary = Double.parseDouble(employeeSalary.getText());
            boolean isChecked = employeeInService.isSelected();



            db.addEmployee(new Employee<>(UUID.randomUUID().toString(), String.format(firstName + " " + lastName), selectedDepartment, salary, 4.8, 5, isChecked));
            String status = String.format("Employee %s successfully added.", (firstName + " " + lastName));
            System.out.println(status);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void deleteEmployeeButtonHandler(ActionEvent event) {
        // Stream hashmap and filter to get object with name contains
        Employee employeeToDelete = db.searchByName(employeeFirstname.getText()).get(0);
        db.removeEmployee(employeeToDelete.getEmployeeId().toString());
        System.out.printf("Successfully deleted %s", employeeFirstname.getText());
        // Get said object ID
        // user db.removeEmployee to remove object from hashmap
    }

    @FXML
    void deleteEmployeeHandler(ActionEvent event) {
        String nameToDelete = (employeeFirstname + " " + employeeLastname);

        if (!nameToDelete.isEmpty()) {
            // Search for employee by name
            List<Employee<String>> matches = db.searchByName(nameToDelete);

            if (!matches.isEmpty()) {
                Employee<String> employeeToRemove = matches.get(0); // Remove first match
                String employeeId = employeeToRemove.getEmployeeId();

                db.removeEmployee(employeeId);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Employee '" + employeeToRemove.getName() + "' deleted.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No employee found with that name.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a name to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    void averageSalaryDepartmentHandler(ActionEvent event) {

    }

    @FXML
    void clearTableHandler(ActionEvent event) {
        employeeTable.getRoot().getChildren().clear();
    }

    @FXML
    void applyRaiseButtonHandler(ActionEvent event) {
//        // Get the employee ID from the text field
//        String employeeID = employeeID.getText().toString();
//
//        // Retrieve the employee from the database
//        Employee employee = db.getEmployee(employeeID);
//
//        // Check if the employee exists
//        if (employee != null) {
//            // Calculate the raise amount (20% of the current salary)
//            double raiseAmount = employee.getSalary() * 0.20;
//
//            // Calculate the new salary
//            double newSalary = employee.getSalary() + raiseAmount;
//
//            // Update the employee's salary
//            employee.setSalary(newSalary);
//
//            // Update the employee in the database (assuming you have an update method)
//            db.updateEmployeeDetails(employeeID, "salary", employee.getSalary() + (0.20 * employee.getSalary()));
//
//            // Display a success message
//            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Raise applied successfully. New salary: " + newSalary);
//            alert.showAndWait();
//        } else {
//            // Display an error message if the employee is not found
//            Alert alert = new Alert(Alert.AlertType.WARNING, "Employee not found with ID: " + employeeID);
//            alert.showAndWait();
//        }
    }

    @FXML
    void computeButtonHandler(ActionEvent event) {

    }

}
