package com.cqu.aisr;

import Controllers.Enum.Route;
import Controllers.Services.AuthService;
import Controllers.Services.PersistsService;
import Controllers.Services.RouteService;
import javafx.scene.control.Label;

public class BaseController {

    public void updateUser(Label userLbl) {
        userLbl.setText(AuthService.get().user().getFirstName());
    }

    public void mapMenuRoute(Label lbl, Route route) {
        lbl.setOnMouseClicked(event -> {
            switch (route) {
                case Route.DASHBOARD:
                    RouteService.redirectTo(Route.DASHBOARD);
                    break;
                case Route.APPLICATION:
                    if (PersistsService.get().applicantsData().size() == 0) {
                        RouteService.redirectToWithMessage(Route.APPLICATION, "No Applicants data Recorded!");
                    } else {
                        RouteService.redirectTo(Route.APPLICATION);
                    }
                    break;
                case Route.APPLICATION_CREATE:
                    RouteService.redirectTo(Route.APPLICATION_CREATE);
                    break;
                case Route.VACANCIES:
                    RouteService.redirectToWithMessage(Route.VACANCIES, "Feature not finished");
                    break;
                case Route.REPORT:
                    RouteService.redirectToWithMessage(Route.REPORT, "Feature not finished");
                    break;
                case Route.STAFF:
                    RouteService.redirectToWithMessage(Route.STAFF, "Feature not finished");
                    break;
                case Route.LOGOUT:
                    RouteService.redirectTo(Route.LOGOUT);
                    break;

                default:
                    break;
            }
        });
    }
}
