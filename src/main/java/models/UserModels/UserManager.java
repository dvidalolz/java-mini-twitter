package main.java.models.UserModels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.exceptions.DuplicateIDException;
import main.java.exceptions.NoUserException;
import main.java.models.interfaces.AnalyticsElement;
import main.java.models.interfaces.AnalyticsVisitor;


/**
 * Singleton class managing all users, user groups, and tweets in the system.
 * It provides methods to create users/groups, retrieve them, and manage tweets.
 */
public class UserManager implements AnalyticsElement {
    private static UserManager instance;
    private UserGroup rootGroup;
    private Map<String, User> userMap;
    private Map<String, UserGroup> userGroupMap;
    private Map<String, List<String>> tweetMap;


    private UserManager() {
        // Initialize maps and the root user group.
        userMap = new HashMap<>();
        userGroupMap = new HashMap<>();
        tweetMap = new HashMap<>();
        rootGroup = new UserGroup("Root");
        userGroupMap.put("Root", rootGroup);
    }

    // Getters for base of users, usergroups and tweets
    public Map<String, User> getUserMap() {
        return userMap;
    }

    public Map<String, UserGroup> getUserGroupMap() {
        return userGroupMap;
    }

    public Map<String, List<String>> getTweetMap() {
        return tweetMap;
    }


    /**
     * Provides access to the singleton instance of UserManager.
     */
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    /**
     * Creates a new user with the specified ID.
     * 
     * @param userID The unique identifier for the new user.
     * @throws DuplicateIDException If a user with the given ID already exists or the ID is invalid.
     */
    public void createUser(String userID) throws DuplicateIDException {
        if (userMap.containsKey(userID)) {
            throw new DuplicateIDException("User " + userID + " already exists");
        } else if (userID.contains(" ")) {
            throw new DuplicateIDException("UserID must not contain spaces");
        } else {
            userMap.put(userID, new User(userID));
            userMap.get(userID);
        }
    }

    /**
     * Retrieves a user by their ID.
     * 
     * @param userID The ID of the user to retrieve.
     * @return The User object associated with the given ID.
     * @throws NoUserException If no user exists with the specified ID.
     */
    public User getUserByID(String userID) throws NoUserException {
        if (!userMap.containsKey(userID)) {
            throw new NoUserException("Attempted get on user not found");
        } else {
            return userMap.get(userID);
        }
    }

    /**
     * Creates a new user group with the specified ID.
     * 
     * @param userGroupID The unique identifier for the new user group.
     * @throws DuplicateIDException If a group with the given ID already exists.
     */
    public void createUserGroup(String userGroupID) throws DuplicateIDException {
        if (userGroupMap.containsKey(userGroupID)) {
            throw new DuplicateIDException("User group " + userGroupID + " already exists");
        } else {
            userGroupMap.put(userGroupID, new UserGroup(userGroupID));
        }
    }

    /**
     * Retrieves a user group by its ID.
     * 
     * @param userGroupID The ID of the group to retrieve.
     * @return The UserGroup object associated with the given ID.
     * @throws NoUserException If no group exists with the specified ID.
     */
    public UserGroup getUserGroupByID(String userGroupID) throws NoUserException {
        if (!userGroupMap.containsKey(userGroupID)) {
            throw new NoUserException("Attempted get on user group not found");
        } else {
            return userGroupMap.get(userGroupID);
        }
    }


    /**
     * Adds a tweet to the tweet map under the specified user ID.
     * 
     * @param userID The ID of the user posting the tweet.
     * @param tweet The content of the tweet.
     */
    public void addTweet(String userID, String tweet) {

        List<String> tweets = tweetMap.get(userID);

        // if tweetlist doesn't exist, create and put into tweepMap, otherwise, add tweet
        if (tweets == null) {
            tweets = new ArrayList<>();
            tweets.add(tweet);
            tweetMap.put(userID, tweets);
        } else {
            tweets.add(tweet);
        }
    }

    /**
     * Retrieves all tweets posted by a specific user.
     * 
     * @param userID The ID of the user whose tweets are to be retrieved.
     * @return A list of tweets posted by the user.
     * @throws NoUserException If no user exists with the specified ID.
     */
    public List<String> getTweetsByUserID(String userID) throws NoUserException {
        if (!tweetMap.containsKey(userID)) {
            throw new NoUserException("Attempted get tweet on user not found");
        } else {
            return tweetMap.get(userID);
        }
    }

    @Override
    public void accept(AnalyticsVisitor visitor) {
        visitor.visit(this);
    }

}
