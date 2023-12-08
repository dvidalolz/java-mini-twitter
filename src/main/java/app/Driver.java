package main.java.app;

import main.java.exceptions.DuplicateIDException;
import main.java.exceptions.NoUserException;
import main.java.models.UserModels.User;
import main.java.models.UserModels.UserFunction;
import main.java.models.UserModels.UserManager;

public class Driver {

    public static void main(String[] args) {
        String timeString = "13:45"; // example time
        try {
            int totalMinutes = convertTimeToInt(timeString);
            System.out.println("Total minutes: " + totalMinutes);
        } catch (NumberFormatException e) {
            System.out.println("Invalid time format.");
        }
    }

    public static int convertTimeToInt(String timeString) throws NumberFormatException {
        String[] parts = timeString.split(":");
        if (parts.length != 2) {
            throw new NumberFormatException("Invalid time format");
        }

        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        // Convert hours to minutes and add the minute part
        return hours * 60 + minutes;
    }

}
