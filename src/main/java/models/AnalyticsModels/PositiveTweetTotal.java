package main.java.models.AnalyticsModels;

import java.util.List;
import java.util.Map;

import main.java.models.UserModels.UserManager;
import main.java.models.interfaces.AnalyticsVisitor;

/**
 * Counts the total number of positive tweets across all users.
 * A tweet is considered positive if it contains words "good", "great", or "cool".
 */
public class PositiveTweetTotal implements AnalyticsVisitor {

    private int positiveTweetTotal;

    /**
     * Iterates over all tweets, incrementing the count for those containing positive words.
     * 
     * @param userManager Provides access to all user tweets.
     */
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
