package main.java.models.UserModels;

import main.java.exceptions.DuplicateIDException;
import main.java.exceptions.NoUserException;

public class UserFunction {
    private User user;
    private UserManager userManager = UserManager.getInstance();
    private UserNotifier userNotifier;

    public UserFunction(User user) throws NoUserException {
        this.user = user;
        this.userNotifier = new UserNotifier();
    }

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

    public void addFollower(String userID) throws DuplicateIDException {
        if (user.getFollowers().contains(userID)) {
            throw new DuplicateIDException("Already followed by " + userID);
        } else {
            user.setFollowers(userID);
        }
    }

    public void tweet(String tweet) throws NoUserException {
        userManager.addTweet(user.getID(), tweet);
        user.updateNewsFeed(user.getID(), tweet);
        userNotifier.initObservers(user);
        userNotifier.notifyObservers(user.getID(), tweet);
    }
}
