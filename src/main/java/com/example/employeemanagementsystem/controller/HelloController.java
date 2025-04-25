package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.model.Employee;
import com.example.employeemanagementsystem.repository.EmployeeDatabase;
import com.example.employeemanagementsystem.service.EmployeePerformanceComparator;
import com.example.employeemanagementsystem.service.EmployeeSalaryComparator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.*;

public class HelloController {
    EmployeeDatabase<String> db = new EmployeeDatabase<>();

    // Set up the logger
    public static final Logger logger = Logger.getLogger(HelloController.class.getName());

    static {
            logger.setLevel(Level.ALL);
            for (Handler handler: logger.getHandlers()) {
                handler.setLevel(Level.ALL);
            }
    }

    // get the n decimal place of any float
    static double round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

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
    private Text averageSalaryText;

    @FXML
    private TextField employeeSalary;

    @FXML
    private CheckBox employeeInService;

    @FXML
    private Button deleteEmployeeButton;

    @FXML
    private Button applyRaiseButton;

    @FXML
    private ComboBox sortEmployees;


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
        // Set comboBox options
        departmentComboBox.getItems().addAll("HR", "IT", "Finance");
        sortEmployees.getItems().addAll("Salary", "Performance", "Experience");
        averageSalaryDepartment.getItems().addAll("HR", "IT", "Finance");
        filterComboBox.getItems().addAll("Department", "Name", "Performance", "Salary");

        // Add sample employees with exception handling
        try {
            db.addEmployee(new Employee<>(UUID.randomUUID().toString(), "Alice Johnson", "HR", 55000, 4.6, 5, true));
            db.addEmployee(new Employee<>(UUID.randomUUID().toString(), "Bob Smith", "IT", 70000, 4.2, 8, true));
            db.addEmployee(new Employee<>(UUID.randomUUID().toString(), "Carol White", "Finance", 64000, 4.8, 4, false));
            db.addEmployee(new Employee<>(UUID.randomUUID().toString(), "David Brown", "IT", 85000, 3.9, 10, true));
            db.addEmployee(new Employee<>(UUID.randomUUID().toString(), "Eva Green", "HR", 53000, 4.9, 2, true));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "There was a problem adding employee(s):", e);
        }

        // Set cell value factories (bind to properties)
        treeTableid.setCellValueFactory(param -> param.getValue().getValue().employeeIdProperty());
        treeTableName.setCellValueFactory(param -> param.getValue().getValue().nameProperty());
        treeTableDepartment.setCellValueFactory(param -> param.getValue().getValue().departmentProperty());
        treeTableSalary.setCellValueFactory(param -> param.getValue().getValue().salaryProperty());
        treeTablePerformance.setCellValueFactory(param -> param.getValue().getValue().performanceRatingProperty());
        treeTableExperience.setCellValueFactory(param -> param.getValue().getValue().yearsOfExperienceProperty());
        treeTableStatus.setCellValueFactory(param -> param.getValue().getValue().isActiveProperty());

        TreeItem<Employee<String>> root = new TreeItem<>(new Employee<>("root", "", "", 0, 0, 0, false));
        root.setExpanded(true);

        ArrayList<Employee> employees = new ArrayList<>(db.getAllEmployees());
        for (Employee employee: employees) {
            root.getChildren().add(new TreeItem<>(new Employee<>(employee.getEmployeeId().toString(), employee.getName(), employee.getDepartment(), employee.getSalary(), employee.getPerformanceRating(), employee.getYearsOfExperience(), employee.isActive())));
        }

        employeeTable.setRoot(root);
        employeeTable.setShowRoot(false); // Hide root from view

    }

    @FXML
    void sortComboBoxHandler(ActionEvent event) {
        // Setup treetable root
        TreeItem<Employee<String>> root = new TreeItem<>(new Employee<>("root", "", "", 0, 0, 0, false));
        root.setExpanded(true);

        ArrayList<Employee> employees = new ArrayList<>(db.getAllEmployees());
        for (Employee employee: employees) {
            root.getChildren().add(new TreeItem<>(new Employee<>(employee.getEmployeeId().toString(), employee.getName(), employee.getDepartment(), employee.getSalary(), employee.getPerformanceRating(), employee.getYearsOfExperience(), employee.isActive())));
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
        // get search option and query
        String searchBy = filterComboBox.getValue();
        String query = searchFilter.getText();

        TreeItem<Employee<String>> root = new TreeItem<>(new Employee<>("","","",0.0,0.0,0, false));
        root.setExpanded(true);

        // Display results as per search option and query
        switch (searchBy) {
            case "Name":
                List<Employee<String>> nameResults = db.searchByName(query);
                for (Employee<String> emp: nameResults) {
                    TreeItem<Employee<String>> item = new TreeItem<>(emp);
                    root.getChildren().add(item);
                }
                break;
            case "Department":
                List<Employee<String>> departmentResults = db.findByDepartment(query);
                for (Employee<String> emp: departmentResults) {
                    TreeItem<Employee<String>> item = new TreeItem<>(emp);
                    root.getChildren().add(item);
                }
                break;
            case "Performance":
                List<Employee<String>> performanceResults = db.highPerformers(4.5);
                for (Employee<String> emp: performanceResults) {
                    TreeItem<Employee<String>> item = new TreeItem<>(emp);
                    root.getChildren().add(item);
                }
                break;
            case "Salary":
                String[] salaryRange = query.split(",");
                double minrange = Double.parseDouble(salaryRange[0]);
                double maxrange = Double.parseDouble(salaryRange[1]);
                List<Employee<String>> salaryResults = db.employeesInSalaryRange(minrange, maxrange);
                for (Employee<String> emp: salaryResults) {
                    TreeItem<Employee<String>> item = new TreeItem<>(emp);
                    root.getChildren().add(item);
                }
                break;
        }
        employeeTable.setRoot(root);
        employeeTable.setShowRoot(false);
    }

    @FXML
    void sortEmployeesHandler(ActionEvent event) {
        String sortBy = sortEmployees.getValue().toString();
        TreeItem<Employee<String>> root = new TreeItem<>(new Employee<>("","","",0.0,0.0,0, false));
        root.setExpanded(true);

        try {
            switch (sortBy) {
                case "Experience":
                    List<Employee<String>> sortedByExperience = db.getAllEmployees();
                    sortedByExperience.sort(new EmployeeSalaryComparator<>());
                    System.out.println();
                    System.out.println("Employees Sorted by Performance:");
                    System.out.println();

//                sortedByExperience.forEach(System.out::println);

                    for (Employee<String> emp : sortedByExperience) {
                        TreeItem<Employee<String>> item = new TreeItem<>(emp);
                        root.getChildren().add(item);
                    }

                    employeeTable.setRoot(root);
                    employeeTable.setShowRoot(false);
                    break;
                case "Salary":
                    List<Employee<String>> sortedBySalary = db.getAllEmployees();
                    sortedBySalary.sort(new EmployeeSalaryComparator<>());
                    System.out.println();
                    System.out.println("Employees Sorted by Performance:");
                    System.out.println();

//                sortedBySalary.forEach(System.out::println);

                    for (Employee<String> emp : sortedBySalary) {
                        TreeItem<Employee<String>> item = new TreeItem<>(emp);
                        root.getChildren().add(item);
                    }

                    employeeTable.setRoot(root);
                    employeeTable.setShowRoot(false);
                    break;
                case "Performance":
                    List<Employee<String>> sortedByPerformance = db.getAllEmployees();
                    sortedByPerformance.sort(new EmployeePerformanceComparator<>());
                    System.out.println();
                    System.out.println("Employees Sorted by Performance:");
                    System.out.println();

//                sortedByPerformance.forEach(System.out::println);

                    for (Employee<String> emp : sortedByPerformance) {
                        TreeItem<Employee<String>> item = new TreeItem<>(emp);
                        root.getChildren().add(item);
                    }

                    employeeTable.setRoot(root);
                    employeeTable.setShowRoot(false);
                    break;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "There was a problem with sorting employees", e);
        } finally {

        }
    }

    @FXML
    void departmentComboBoxHandler(ActionEvent event) {

    }

    @FXML
    void employeeInServiceHandler(ActionEvent event) {

    }

    @FXML
    void addEmployeeButtonHandler(ActionEvent event) {
        // Get input fields
        try {
        String firstName = employeeFirstname.getText();
        String lastName = employeeLastname.getText();
        String selectedDepartment = departmentComboBox.getSelectionModel().getSelectedItem().toString();
        Double salary = Double.parseDouble(employeeSalary.getText());
        System.out.println("Checkbox selected? " + employeeInService.isSelected());
        boolean isChecked = employeeInService.isSelected();



        // Add employee to treechild

            db.addEmployee(new Employee<>(UUID.randomUUID().toString(), String.format(firstName + " " + lastName), selectedDepartment, salary, 4.8, 5, isChecked));
            String status = String.format("Employee %s successfully added.", (firstName + " " + lastName));
            System.out.println(status);
        } catch (Exception e) {
            System.out.printf("Error when getting field values %s", e);
        }

    }

    @FXML
    void deleteEmployeeButtonHandler(ActionEvent event) {
        // Stream hashmap and filter to get object with name contains
        Employee employeeToDelete = db.searchByName(employeeFirstname.getText()).get(0);
        try {
            db.removeEmployee(employeeToDelete.getEmployeeId().toString()); // remove employee from database
        } catch (Exception e) {
            logger.log(Level.SEVERE, "There was an issue deleting an employee: ", e);
        } finally {
            System.out.printf("Successfully deleted %s", employeeFirstname.getText());
        }
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
        // Clear TreeTableView
        employeeTable.getRoot().getChildren().clear();
    }

    @FXML
    void applyRaiseButtonHandler(ActionEvent event) {
        try {
            db.giveRaise(4.5, 1000);
            System.out.println("Ghs1000.00 raise added to all high performance employees.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "There was a problem giving raises to employees: ", e);
        } finally {}
    }

    @FXML
    void computeSalaryButtonHandler(ActionEvent event) {
        String dep = averageSalaryDepartment.getValue();
        try {
            switch (dep) {
                case "IT":
                    double itAverage = db.averageSalaryByDepartment(dep);
                    double roundedIT = round((float) itAverage, 2);
                    averageSalaryText.setText(String.format("%f", roundedIT));
                    break;
                case "Finance":
                    double financeAverage = db.averageSalaryByDepartment(dep);
                    BigDecimal bdFinance = new BigDecimal(financeAverage).setScale(2, RoundingMode.HALF_UP);
                    double roundedFinance = bdFinance.doubleValue();
                    averageSalaryText.setText(String.format("%f", roundedFinance));
                    break;
                case "HR":
                    double hrAverage = db.averageSalaryByDepartment(dep);
                    BigDecimal bdHR = new BigDecimal(hrAverage).setScale(2, RoundingMode.HALF_UP);
                    double roundedHR = bdHR.doubleValue();
                    averageSalaryText.setText(String.format("%f", roundedHR));
                    break;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "There was a problem computing department salary average", e);
        } finally {

        }
    }

}
