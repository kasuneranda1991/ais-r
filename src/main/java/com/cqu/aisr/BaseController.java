package com.cqu.aisr;

import Enum.Route;
import Services.AuthService;
import Services.RouteService;
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
                    RouteService.redirectTo(Route.APPLICATION);
                    break;
                case Route.APPLICATION_CREATE:
                    RouteService.redirectTo(Route.APPLICATION_CREATE);
                    break;
                case Route.VACANCIES:
                    RouteService.redirectTo(Route.VACANCIES);
                    break;
                case Route.REPORT:
                    RouteService.redirectTo(Route.REPORT);
                    break;
                case Route.STAFF:
                    RouteService.redirectTo(Route.STAFF);
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
