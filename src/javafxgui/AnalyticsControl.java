package javafxgui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AnalyticsControl {
    
    @FXML
    Label Total;

    public void displayTotal(String type, int s) {
        Total.setText(type + " total " + s);
    }
}
