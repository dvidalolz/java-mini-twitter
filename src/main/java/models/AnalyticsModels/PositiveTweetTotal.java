package main.java.models.AnalyticsModels;

import java.util.List;
import java.util.Map;

import main.java.models.UserModels.UserManager;
import main.java.models.interfaces.AnalyticsVisitor;

public class PositiveTweetTotal implements AnalyticsVisitor {

    private int positiveTweetTotal;

    @Override
    public void visit(UserManager userManager) {
        Map<String, List<String>> allTweets = userManager.getTweetMap();

        for (Map.Entry<String, List<String>> entry : allTweets.entrySet()) {
            List<String> tweets = entry.getValue();

            for (String tweet : tweets) {
                String lowerCaseTweet = tweet.toLowerCase();
                if (lowerCaseTweet.contains("good") || lowerCaseTweet.contains("great") || lowerCaseTweet.contains("cool")) {
                    positiveTweetTotal += 1;
                }
            }
        }
    }

    @Override
    public int getTotal() {
        return positiveTweetTotal;
    }

}
