package main.java.models.AnalyticsModels;

import java.util.List;
import java.util.Map;

import main.java.models.UserModels.UserManager;
import main.java.models.interfaces.AnalyticsVisitor;


/**
 * Analytic tool to count the total number of tweets across all users.
 */
public class TweetTotal implements AnalyticsVisitor {

    private int tweetTotal;

    /**
     * Visits the UserManager to accumulate the total count of tweets from all users.
     *
     * @param userManager The source of user data and their tweets.
     */
    @Override
    public void visit(UserManager userManager) {
        Map<String, List<String>> tweetMap = userManager.getTweetMap();

        for (List<String> tweets : tweetMap.values()) {
            tweetTotal += tweets.size();
        }
    }

    @Override
    public int getTotal() {
        return tweetTotal;
    }

}
