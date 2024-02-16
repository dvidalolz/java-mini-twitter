package main.java.models.interfaces;

import main.java.exceptions.NoUserException;


/**
 * Defines the Observer interface for implementing the Observer pattern.
 * This interface is used for objects that should be notified of changes, such as updates in a user's news feed or tweet times.
 */
public interface Observer {

    /**
     * Updates the observer's news feed with a new tweet from a user.
     * 
     * @param userID The ID of the user who posted the tweet.
     * @param tweet The content of the tweet.
     * @throws NoUserException If the user ID does not correspond to an existing user.
     */
    void updateNewsFeed(String userID, String tweet) throws NoUserException;

    /**
     * Updates the observer's information related to tweet times to reflect the most recent tweet time.
     */
    public void updateTweetTime();
}
