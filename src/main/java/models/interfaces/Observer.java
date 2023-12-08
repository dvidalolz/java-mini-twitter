package main.java.models.interfaces;

import main.java.exceptions.NoUserException;

public interface Observer {
    void updateNewsFeed(String userID, String tweet) throws NoUserException;
    public void updateTweetTime();
}
