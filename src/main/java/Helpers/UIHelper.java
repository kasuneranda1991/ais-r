/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helpers;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 *
 * @author kasun
 */
public class UIHelper {

    public static void setElementsVisible(Boolean isVisible, Object... uiObjects) {

        for (Object obj : uiObjects) {
            if (obj instanceof Label) {
                Label el = (Label) obj;
                el.setVisible(isVisible);
            } else if (obj instanceof ImageView) {
                ImageView el = (ImageView) obj;
                el.setVisible(isVisible);
            }
        }
    }

    private static <T> T getElement(T el) {
        if (el instanceof Label || el instanceof ImageView) {
            return el;
        }
        return null;
    }
}
