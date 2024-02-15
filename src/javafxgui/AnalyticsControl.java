package javafxgui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller for handling the display of analytics information in the UI.
 * It updates a label with the total count of a specified entity type (e.g., users, groups, tweets).
 */
public class AnalyticsControl {
    
    @FXML
    private Label Total; // Label to display the total count.

    /**
     * Updates the label to display the total count for a specific type of entity.
     * 
     * @param type The type of entity being counted (e.g., "User", "Group").
     * @param s The total count to display.
     */
    public void displayTotal(String type, int s) {
        Total.setText(type + " total: " + s); // Sets the text of the Total label.
    }
}
