package main.java.models.UserModels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.exceptions.DuplicateIDException;
import main.java.exceptions.NoUserException;
import main.java.models.interfaces.AnalyticsElement;
import main.java.models.interfaces.AnalyticsVisitor;

public class UserManager implements AnalyticsElement {
    private static UserManager instance;
    private UserGroup rootGroup;
    private Map<String, User> userMap;
    private Map<String, UserGroup> userGroupMap;
    private Map<String, List<String>> tweetMap;

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public Map<String, UserGroup> getUserGroupMap() {
        return userGroupMap;
    }

    public Map<String, List<String>> getTweetMap() {
        return tweetMap;
    }

    private UserManager() {
        userMap = new HashMap<>();
        userGroupMap = new HashMap<>();
        tweetMap = new HashMap<>();
        rootGroup = new UserGroup("Root");
        userGroupMap.put("Root", rootGroup);
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

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

    public User getUserByID(String userID) throws NoUserException {
        if (!userMap.containsKey(userID)) {
            throw new NoUserException("Attempted get on user not found");
        } else {
            return userMap.get(userID);
        }
    }

    public void createUserGroup(String userGroupID) throws DuplicateIDException {
        if (userGroupMap.containsKey(userGroupID)) {
            throw new DuplicateIDException("User group " + userGroupID + " already exists");
        } else {
            userGroupMap.put(userGroupID, new UserGroup(userGroupID));
        }
    }

    public UserGroup getUserGroupByID(String userGroupID) throws NoUserException {
        if (!userGroupMap.containsKey(userGroupID)) {
            throw new NoUserException("Attempted get on user group not found");
        } else {
            return userGroupMap.get(userGroupID);
        }
    }

    public void addTweet(String userID, String tweet) {

        List<String> tweets = tweetMap.get(userID);

        // if tweetlist doesn't exist, create and put into tweepMap, otherwise, add
        // tweet
        if (tweets == null) {
            tweets = new ArrayList<>();
            tweets.add(tweet);
            tweetMap.put(userID, tweets);
        } else {
            tweets.add(tweet);
        }
    }

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
