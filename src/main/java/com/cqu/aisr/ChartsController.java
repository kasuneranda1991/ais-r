/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.cqu.aisr;

import Controllers.Enum.Charts;
import Controllers.Enum.Department;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import Controllers.Enum.Route;
import Controllers.Services.PersistsService;
import DAO.ApplicantDAO;
import Models.Applicant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author akila
 */
public class ChartsController extends BaseController implements Initializable {

    /**
     * Initializes the controller class.
     */
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
    private VBox applicationSubNav;
    @FXML
    private Label staffSideMenu;
    @FXML
    private Label reportsSideMenu;
    @FXML
    private ChoiceBox<String> chartType;
    @FXML
    private AnchorPane borderPane123;
    @FXML
    private Pane chartPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        updateUser(loggedInUserlbl);
        mapMenuRoute(homeSideMenu, Route.DASHBOARD);
        mapMenuRoute(applicationsSideMenu, Route.APPLICATION);
        mapMenuRoute(vacanciesSideMenu, Route.VACANCIES);
        mapMenuRoute(staffSideMenu, Route.STAFF);
        mapMenuRoute(reportsSideMenu, Route.REPORT);
        mapMenuRoute(logOutMenuItem, Route.LOGOUT);
        chartType.setItems(Charts.getValues());

    }

    @FXML
    public void changeChart() {
        String selectedChart = chartType.getValue();
        chartPane.getChildren().clear();
        if (selectedChart.equals(Charts.BAR.getValue())) {
            System.out.println("Bar Chart");

            CategoryAxis xAxis = new CategoryAxis();
            xAxis.setLabel("Departments");

            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("Number of Staff");

            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            barChart.setTitle("Applicant per Department");

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Applicants per Department");

            ApplicantDAO applicantDAO = new ApplicantDAO();
            try {
                Map<String, Integer> applicantCountByDepartment = applicantDAO.getApplicantCountByDepartment();
                AtomicInteger index = new AtomicInteger(0);
                for (Map.Entry<String, Integer> entry : applicantCountByDepartment.entrySet()) {
                    XYChart.Data<String, Number> data = new XYChart.Data<>(entry.getKey(), entry.getValue());
                    series.getData().add(data);

                    // Apply color dynamically
                    int colorIndex = index.getAndIncrement(); // Get current value and increment
                    data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            newValue.setStyle("-fx-bar-fill: " + getColor(colorIndex));
                        }
                    });
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // You can show an alert here if needed
            }

            barChart.getData().add(series);
            chartPane.getChildren().add(barChart);

        } else if (selectedChart.equals(Charts.PIE.getValue())) {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            ApplicantDAO applicantDAO = new ApplicantDAO();
            try {
                Map<String, Integer> applicantCountByDepartment = applicantDAO.getApplicantCountByDepartment();
                for (Map.Entry<String, Integer> entry : applicantCountByDepartment.entrySet()) {
                    pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
                }

                PieChart pieChart = new PieChart(pieChartData);
                pieChart.setTitle("Applicant per Department");
                chartPane.getChildren().add(pieChart);

            } catch (SQLException e) {
                e.printStackTrace();
                // You can show an alert here if needed
            }
        }
    }

    private String getColor(int index) {
        String[] colors = { "#ff7f0e", "#2ca02c", "#d62728", "#9467bd", "#8c564b", "#e377c2", "#7f7f7f", "#bcbd22",
                "#17becf" };
        return colors[index % colors.length];
    }

}
