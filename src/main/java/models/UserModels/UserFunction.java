package main.java.models.UserModels;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import main.java.exceptions.DuplicateIDException;
import main.java.exceptions.NoUserException;


/**
 * Provides specific functionalities for a User, such as following others and posting tweets.
 */
public class UserFunction {
    private User user;
    private UserManager userManager = UserManager.getInstance();
    private UserNotifier userNotifier;
    private static LocalTime lastUpdateTime = LocalTime.now();

    /**
     * Initializes the UserFunction for a specific User.
     */
    public UserFunction(User user) throws NoUserException {
        this.user = user;
        this.userNotifier = new UserNotifier();
    }

    public String getLocalTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return lastUpdateTime.format(formatter);
    }

    /**
     * Allows a user to follow another user by their userID.
     */
    public void follow(String userID) throws NoUserException {
        try {
            user.setFollowing(userID);
            User followedUser = userManager.getUserByID(userID);
            followedUser.setFollowers(user.getID());

            // Update the followed user's notifier to include the new follower
            UserNotifier followedUserNotifier = new UserNotifier();
            followedUserNotifier.initObservers(followedUser);
        } catch (NoUserException e) {
            System.err.println("Attempted to follow non-existing user with ID: " + userID);
            e.printStackTrace();
        }
    }

    /**
     * Adds a follower to this user's list of followers.
     */
    public void addFollower(String userID) throws DuplicateIDException {
        if (user.getFollowers().contains(userID)) {
            throw new DuplicateIDException("Already followed by " + userID);
        } else {
            user.setFollowers(userID);
        }
    }

    /**
     * Handles the posting of a tweet by the user, updating all relevant feeds and notifying observers.
     */
    public void tweet(String tweet) throws NoUserException {
        // add tweet to list of all tweets (map in userManager)
        userManager.addTweet(user.getID(), tweet);

        // update user's own newsFeed with tweet
        user.updateNewsFeed(user.getID(), tweet);

        // initialize observers (users whose newsFeeds will get updated)
        userNotifier.initObservers(user);

        // notify observers of the change
        userNotifier.notifyObservers(user.getID(), tweet);
    }
}
