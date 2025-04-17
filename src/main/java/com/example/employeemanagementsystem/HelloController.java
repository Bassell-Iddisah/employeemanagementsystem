package com.example.employeemanagementsystem;

import java.net.URL;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.text.Text;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Tab dashboard;

    @FXML
    private TreeTableView<Employee<?>> employeeTable;

    @FXML
    private TreeTableColumn<Employee<?>, String> employeeIdColumn;

    @FXML
    private TreeTableColumn<Employee<?>, String> nameColumn;

    @FXML
    private TreeTableColumn<Employee<?>, String> departmentColumn;

    @FXML
    private TreeTableColumn<Employee<?>, Double> salaryColumn;

    @FXML
    private TreeTableColumn<Employee<?>, Double> performanceRatingColumn;

    @FXML
    private TreeTableColumn<Employee<?>, Integer> yearsOfExperienceColumn;

    @FXML
    private TreeTableColumn<Employee<?>, Boolean> activeStatusColumn;

    @FXML
    private ToggleGroup activeEmployee;

    @FXML
    private Button addEmployeeButton;

    @FXML
    private Button applyRaiseButton;

    @FXML
    private ComboBox<?> averageSalaryDepartment;

    @FXML
    private Text averageSalaryText;

    @FXML
    private Button computeButton;

    @FXML
    private Button deleteEmployee;

    @FXML
    private ComboBox<String> departmentComboBox;

    @FXML
    private TextField employeeFirstname;

    @FXML
    private TextField employeeID;

    @FXML
    private TextField employeeLastname;

    @FXML
    private TextField employeeSalary;

    @FXML
    private RadioButton employeeServiceNo;

    @FXML
    private RadioButton employeeServiceYes;



    @FXML
    private TreeTableColumn<?, ?> employeeView;

    @FXML
    private ComboBox<String> filterMenuButton;

    @FXML
    private ComboBox<?> honorificMenuButton;

    @FXML
    private Text noOfDepartments;

    @FXML
    private Text noOfEmployees;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchFilter;

    @FXML
    private MenuButton sortButton;

    @FXML
    private TextField employeeIDTextField;

    @FXML
    private ComboBox averageSalaryDepartmentComboBox;

    @FXML
    private Text employeesCountText;

    @FXML
    private Text departmentsCountText;

    private EmployeeDatabase<String> employeeDatabase = new EmployeeDatabase<>();

    @FXML
    void addEmployeeButtonHandler(ActionEvent event) {
        String firstName = employeeFirstname.getText();
        String lastName = employeeLastname.getText();
        String salaryText = employeeSalary.getText();
        String department = (String) departmentComboBox.getValue();
        boolean isActive = employeeServiceYes.isSelected();

        if (firstName.isEmpty() || lastName.isEmpty() || salaryText.isEmpty() || department == null) {
            showAlert("Please fill in all employee details.");
            return;
        }

        try {
            double salary = Double.parseDouble(salaryText);
            String employeeId = UUID.randomUUID().toString();
            Employee<String> newEmployee = new Employee<>(employeeId, firstName + " " + lastName, department, salary, 3.0, 0, isActive);
            employeeDatabase.addEmployee(newEmployee);
            refreshEmployeeTable();
            updateDashboardStats();
            clearEmployeeFields();
        } catch (NumberFormatException e) {
            showAlert("Invalid salary format.");
        }
    }

    @FXML
    void applyRaiseButtonHandler(ActionEvent event) {
        String employeeId = employeeIDTextField.getText();
        if (employeeId == null || employeeId.isEmpty()) {
            showAlert("Please enter an Employee ID to apply a raise.");
            return;
        }

        // For simplicity, let's assume a fixed raise amount and threshold
        double raiseAmount = 5000.0;
        double thresholdRating = 3.0;

        employeeDatabase.giveRaise(thresholdRating, raiseAmount);
        refreshEmployeeTable();
    }

    @FXML
    void averageSalaryDepartmentHandler(ActionEvent event) {
        String department = (String) averageSalaryDepartmentComboBox.getValue();
        if (department == null) {
            showAlert("Please select a department to compute average salary.");
            return;
        }
        double avgSalary = employeeDatabase.averageSalaryByDepartment(department);
        averageSalaryText.setText(String.format("%.2f", avgSalary));
    }

    @FXML
    void computeButtonHandler(ActionEvent event) {
        averageSalaryDepartmentHandler(event);
    }

    @FXML
    void deleteEmployeeButtonHandler(ActionEvent event) {
        String employeeId = employeeIDTextField.getText();
        if (employeeId == null || employeeId.isEmpty()) {
            showAlert("Please enter the employee ID to delete.");
            return;
        }
        employeeDatabase.removeEmployee(employeeId);
        refreshEmployeeTable();
        updateDashboardStats();
    }

    @FXML
    void searchButtonHandler(ActionEvent event) {
        String searchText = searchFilter.getText();
        if (searchText == null || searchText.isEmpty()) {
            refreshEmployeeTable();
            return;
        }
        List<Employee<String>> searchResults = employeeDatabase.searchByName(searchText);
        populateEmployeeTable(searchResults);
    }

    @FXML
    void sortHandler(ActionEvent event) {
        //Sorting Functionality
    }

    @FXML
    void initialize() {
        assert dashboard != null : "fx:id=\"dashboard\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
        assert employeeTable != null : "fx:id=\"employeeTable\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
        assert sortButton != null : "fx:id=\"sortButton\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
        assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
        assert searchFilter != null : "fx:id=\"searchFilter\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
        assert filterMenuButton != null : "fx:id=\"filterMenuButton\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
//        assert employeesPane != null : "fx:id=\"employeesPane\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
//        assert employeesCountText != null : "fx:id=\"employeesCountText\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
//        assert departmentsPane != null : "fx:id=\"departmentsPane\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
//        assert departmentsCountText != null : "fx:id=\"departmentsCountText\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
//        assert honorificComboBox != null : "fx:id=\"honorificComboBox\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
        assert employeeFirstname != null : "fx:id=\"employeeFirstname\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
        assert employeeLastname != null : "fx:id=\"employeeLastname\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
        assert employeeSalary != null : "fx:id=\"employeeSalary\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
        assert departmentComboBox != null : "fx:id=\"departmentComboBox\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
        assert employeeServiceYes != null : "fx:id=\"employeeServiceYes\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
        assert activeEmployee != null : "fx:id=\"activeEmployee\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
        assert employeeServiceNo != null : "fx:id=\"employeeServiceNo\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
        assert addEmployeeButton != null : "fx:id=\"addEmployeeButton\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
//        assert deleteEmployeeButton != null : "fx:id=\"deleteEmployeeButton\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
//        assert employeeIDTextField != null : "fx:id=\"employeeIDTextField\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
        assert applyRaiseButton != null : "fx:id=\"applyRaiseButton\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
//        assert averageSalaryDepartmentComboBox != null : "fx:id=\"averageSalaryDepartmentComboBox\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
        assert averageSalaryText != null : "fx:id=\"averageSalaryText\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";
        assert computeButton != null : "fx:id=\"computeButton\" was not injected: check your FXML file 'EmployeeInterface.fxml'.";

        // Initialize columns
        employeeIdColumn = new TreeTableColumn<>("EmployeeID");
        nameColumn = new TreeTableColumn<>("Name");
        departmentColumn = new TreeTableColumn<>("Department");
        salaryColumn = new TreeTableColumn<>("Salary");
        performanceRatingColumn = new TreeTableColumn<>("Performance Rating");
        yearsOfExperienceColumn = new TreeTableColumn<>("Years of Experience");
        activeStatusColumn = new TreeTableColumn<>("Active Status");

        employeeIdColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("employeeId"));
        nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        departmentColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("department"));
        salaryColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("salary"));
        performanceRatingColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("performanceRating"));
        yearsOfExperienceColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("yearsOfExperience"));
        activeStatusColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("isActive"));

        employeeTable.getColumns().setAll(employeeIdColumn, nameColumn, departmentColumn, salaryColumn, performanceRatingColumn, yearsOfExperienceColumn, activeStatusColumn);

        // Populate department and other combo boxes
        departmentComboBox.setItems(FXCollections.observableArrayList("HR", "Engineering", "Sales", "Marketing", "Finance").sorted());
        averageSalaryDepartmentComboBox.setItems(FXCollections.observableArrayList("HR", "Engineering", "Sales", "Marketing", "Finance"));
        filterMenuButton.setItems(FXCollections.observableArrayList("Department", "Salary Range", "Performance").sorted());

        // Load initial data and refresh table
        loadSampleEmployees();
        refreshEmployeeTable();
        updateDashboardStats();
    }

    private void loadSampleEmployees() {
        employeeDatabase.addEmployee(new Employee<>("E001", "Alice Smith", "Engineering", 75000, 4.5, 5, true));
        employeeDatabase.addEmployee(new Employee<>("E002", "Bob Johnson", "Sales", 55000, 3.8, 3, true));
        employeeDatabase.addEmployee(new Employee<>("E003", "Carol White", "HR", 60000, 4.2, 6, true));
        employeeDatabase.addEmployee(new Employee<>("E004", "David Brown", "Marketing", 52000, 3.5, 2, false));
        employeeDatabase.addEmployee(new Employee<>("E005", "Eva Green", "Finance", 70000, 4.7, 7, true));
    }

    private void refreshEmployeeTable() {
        populateEmployeeTable(employeeDatabase.getAllEmployees());
    }

    private void populateEmployeeTable(List<Employee<String>> employees) {
        ObservableList<TreeItem<Employee<?>>> employeeItems = FXCollections.observableArrayList();
        for (Employee<?> emp : employees) {
            employeeItems.add(new TreeItem<>(emp));
        }
        TreeItem<Employee<?>> root = new TreeItem<>(new Employee<>("Root", "", "", 0, 0, 0, false));
        root.getChildren().addAll(employeeItems);

        employeeTable.setRoot(root);
        employeeTable.setShowRoot(false);
    }

    private void updateDashboardStats() {
        employeesCountText.setText(String.valueOf(employeeDatabase.getAllEmployees().size()));
        Set<String> departments = new HashSet<>();
        for (Employee<?> emp : employeeDatabase.getAllEmployees()) {
            departments.add(emp.getDepartment());
        }
        departmentsCountText.setText(String.valueOf(departments.size()));
    }

    private void clearEmployeeFields() {
        employeeFirstname.clear();
        employeeLastname.clear();
        employeeSalary.clear();
        departmentComboBox.setValue(null);
        employeeServiceYes.setSelected(true);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
