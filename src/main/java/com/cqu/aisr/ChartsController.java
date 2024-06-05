/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.cqu.aisr;

import Controllers.Enum.Department;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import Controllers.Enum.Route;
import Controllers.Services.PersistsService;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
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
//        chartType.setItems(Charts.getValues());
        
    }   
    
//    @FXML
//    public void changeChart(){
//        String selectedChart = chartType.getValue();
//        if(selectedChart.equals(Charts.BAR.getValue())){
//            System.out.println("Bar Chart");
//        
//            CategoryAxis xAxis = new CategoryAxis();
//            xAxis.setLabel("Departments");
//
//            NumberAxis yAxis = new NumberAxis();
//            yAxis.setLabel("Number of Staff");
//
//            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
//            barChart.setTitle("Applicant per Department");
//
//            XYChart.Series<String, Number> series = new XYChart.Series<>();
//            series.setName("Applicants per Department");
//
//            series.getData().add(new XYChart.Data<>("IT", 10));
//            series.getData().add(new XYChart.Data<>("HR", 20));
//            series.getData().add(new XYChart.Data<>("Finance", 30));
//            series.getData().add(new XYChart.Data<>("Admin", 40));
//            series.getData().add(new XYChart.Data<>("Management", 50));    
//
//            barChart.getData().add(series);
//            chartPane.getChildren().add(barChart);
//
//        }else if(selectedChart.equals(Charts.PIE.getValue())){
//            System.out.println("Pie Chart");
//        }
//    }

    
}
