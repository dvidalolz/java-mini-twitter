package main.java.models.AnalyticsModels;

import java.util.Map;

import main.java.models.UserModels.User;
import main.java.models.UserModels.UserManager;
import main.java.models.interfaces.AnalyticsVisitor;

public class LastUpdateTime implements AnalyticsVisitor {

    private int lastUpdatedTime;

    @Override
    public void visit(UserManager userManager) {

        // get the user tweetTime, make it the last one
        Map<String, User> userMap = userManager.getUserMap();


        Map.Entry<String, User> firstEntry = userMap.entrySet().iterator().next();
        User firstUser = firstEntry.getValue();
        lastUpdatedTime = convertTimeToInt(firstUser.getTweetTime());

        for (User user : userMap.values()) {
            int currentUserTweetTime = convertTimeToInt(user.getTweetTime());
            if (currentUserTweetTime > lastUpdatedTime) {
                lastUpdatedTime = currentUserTweetTime;
            }
        }
    }

    @Override
    public int getTotal() {
        return lastUpdatedTime;
    }

    private static int convertTimeToInt(String timeString) throws NumberFormatException {
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
