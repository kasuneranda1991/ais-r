/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.cqu.aisr;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import Controllers.Enum.Route;
import Controllers.Services.PersistsService;
import DAO.ApplicantDAO;
import DAO.StaffDAO;
import Models.Applicant;
import Models.Management;
import Models.Staff;
import Models.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author kasun
 */
public class ReportController extends BaseController implements Initializable {

    @FXML
    private Label loggedInUserlbl;
    @FXML
    private Label logOutMenuItem;
    @FXML
    private Label homeSideMenu;
    @FXML
    private Label applicationsSideMenu;
    @FXML
    private Label vacanciesSideMenu;
    @FXML
    private Label staffSideMenu;
    @FXML
    private Label reportsSideMenu;
    @FXML
    private VBox applicationSubNav;
    @FXML
    private Tab totalApplicants, staffCount, applicantsOnDept, listOfRecruits, listOfManagers;
    @FXML
    private Label chartSideMenu;
    @FXML
    private Label profileMenuItem;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        totalApplicants.setContent(
                setReport("Total applicants: " +
                        PersistsService.get().applicantsData().size()));
        staffCount.setContent(
                setReport("Total Staff: " +
                        PersistsService.get().staffData().size() + "\n" +
                        "Total Administrators: " + Staff.totalAdmins() + "\n" +
                        "Total Managers: " + Staff.totalManagers()));

        applicantsOnDept.setContent(setReport(Applicant.stats()));

        try {
            listOfRecruits.setContent(setReportTableApplicant(ApplicantDAO.listAllApplicantsForReport()));
        } catch (SQLException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            listOfManagers.setContent(setReportTableStaff(StaffDAO.listAllStaffForReport()));
        } catch (SQLException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }

        updateUser(loggedInUserlbl);
        mapMenuRoute(homeSideMenu, Route.DASHBOARD);
        mapMenuRoute(applicationsSideMenu, Route.APPLICATION);
        mapMenuRoute(vacanciesSideMenu, Route.VACANCIES);
        mapMenuRoute(staffSideMenu, Route.STAFF);
        mapMenuRoute(reportsSideMenu, Route.REPORT);
        mapMenuRoute(logOutMenuItem, Route.LOGOUT);
        mapMenuRoute(chartSideMenu, Route.CHART);
        mapMenuRoute(profileMenuItem, Route.REGISTRATION);
    }

    public Label setReport(String content) {
        Label label = new Label(content);
        label.setStyle(
                "-fx-background-color: #022c56;" +
                        "-fx-background-radius: 5, 4, 3, 5;" +
                        "-fx-background-radius: 5, 4, 3, 5;" +
                        "-fx-background-insets: 0, 1, 2, 0;" +
                        "-fx-text-fill: white;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.6), 5, 0, 0, 1);" +
                        "-fx-text-fill: linear-gradient(white, #d0d0d0);" +
                        "-fx-font-size: 15px;" +
                        "-fx-padding: 10 20 10 20;");
        return label;
    }

    public TableView<Applicant> setReportTableApplicant(List<Applicant> applicants) {
        // Convert the list of applicants to an ObservableList

        ObservableList<Applicant> observableApplicants = FXCollections.observableArrayList(applicants);

        // Create a TableView
        TableView<Applicant> tableView = new TableView<>();

        // Define columns
        TableColumn<Applicant, String> nameColumn = new TableColumn<>("Firs Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Applicant, Integer> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Applicant, Integer> depColumn = new TableColumn<>("Department");
        depColumn.setCellValueFactory(new PropertyValueFactory<>("department"));

        TableColumn<Applicant, Integer> branchColumn = new TableColumn<>("Branch");
        branchColumn.setCellValueFactory(new PropertyValueFactory<>("branch"));

        TableColumn<Applicant, Integer> intvColumn = new TableColumn<>("Interview Date");
        intvColumn.setCellValueFactory(new PropertyValueFactory<>("interviewDate"));

        TableColumn<Applicant, Integer> eduColumn = new TableColumn<>("Qualification");
        eduColumn.setCellValueFactory(new PropertyValueFactory<>("edu"));

        // Add columns to the TableView
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(lastNameColumn);
        tableView.getColumns().add(depColumn);
        tableView.getColumns().add(branchColumn);
        tableView.getColumns().add(intvColumn);
        tableView.getColumns().add(eduColumn);

        // Bind the ObservableList to the TableView
        tableView.setItems(observableApplicants);

        // Return the TableView
        return tableView;
    }

    // setReportTableStaff
    public TableView<User> setReportTableStaff(List<User> staffs) {
        // Convert the list of staffs to an ObservableList
        ObservableList<User> observableStaffs = FXCollections.observableArrayList(staffs);

        // Create a TableView
        TableView<User> tableView = new TableView<>();

        // Define columns
        TableColumn<User, String> nameColumn = new TableColumn<>("First Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<User, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<User, String> addrColumn = new TableColumn<>("Address");
        addrColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<User, String> mlColumn = new TableColumn<>("Management Level");
        mlColumn.setCellValueFactory(cellData -> {
            String managementLevel = "N/A";
            if (cellData.getValue() instanceof Management) {
                managementLevel = ((Management)(cellData.getValue())).getManagement_lvl();
            }
            return new SimpleStringProperty(managementLevel);
        });

        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<User, String> branchColumn = new TableColumn<>("Branch");
        branchColumn.setCellValueFactory(new PropertyValueFactory<>("branch"));

        // Add columns to the TableView
        tableView.getColumns().addAll(nameColumn, lastNameColumn, addrColumn, mlColumn, roleColumn, branchColumn);

        // Bind the ObservableList to the TableView
        tableView.setItems(observableStaffs);

        // Return the TableView
        return tableView;
    }

}
