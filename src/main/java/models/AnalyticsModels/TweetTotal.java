package main.java.models.AnalyticsModels;

import java.util.List;
import java.util.Map;

import main.java.models.UserModels.UserManager;
import main.java.models.interfaces.AnalyticsVisitor;

public class TweetTotal implements AnalyticsVisitor {

    private int tweetTotal;

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
