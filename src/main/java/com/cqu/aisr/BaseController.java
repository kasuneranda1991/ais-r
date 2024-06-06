package com.cqu.aisr;

import Controllers.Enum.Route;
import Controllers.Services.AuthService;
import Controllers.Services.AuthenticateService;
import Controllers.Services.PersistsService;
import Controllers.Services.RouteService;
import javafx.scene.control.Label;

public class BaseController {

    public void updateUser(Label userLbl) {
        System.out.println("csv "+ AuthService.get().user().getCSV());
        userLbl.setText(AuthService.get().user().getFirstName());
    }

    public void mapMenuRoute(Label lbl, Route route) {
        lbl.setOnMouseClicked(event -> {
            if (route == Route.DASHBOARD) {
                RouteService.redirectTo(Route.DASHBOARD);
            } else if (route == Route.APPLICATION) {
                if (PersistsService.get().applicantsData().size() == 0) {
                    RouteService.redirectToWithMessage(Route.APPLICATION, "No Applicants data Recorded!");
                } else {
                    RouteService.redirectTo(Route.APPLICATION);
                }
            } else if (route == Route.APPLICATION_CREATE) {
                RouteService.redirectTo(Route.APPLICATION_CREATE);
            } else if (route == Route.VACANCIES) {
                RouteService.redirectToWithMessage(Route.VACANCIES, "Feature not finished");
            } else if (route == Route.REPORT) {
                RouteService.redirectToWithMessage(Route.REPORT, "Feature not finished");
            } else if (route == Route.STAFF) {
                RouteService.redirectToWithMessage(Route.STAFF, "Feature not finished");
            } else if (route == Route.CHART) {
                RouteService.redirectTo(Route.CHART);
            } else if (route == Route.LOGOUT) {
                AuthenticateService.logout();
                RouteService.redirectTo(Route.LOGOUT);
            } else if (route == Route.REGISTRATION) {
                RouteService.redirectTo(Route.REGISTRATION);
            }
        });
    }
}
