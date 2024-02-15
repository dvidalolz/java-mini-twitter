package javafxgui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.java.exceptions.NoUserException;
import main.java.models.UserModels.User;
import main.java.models.UserModels.UserFunction;
import main.java.models.UserModels.UserManager;

/**
 * Manages user interactions within the user panel, such as following users and posting tweets.
 * It updates the UI with the latest following list and news feed.
 */

public class UserControl {

    @FXML
    TextField followTextField, tweetTextField;

    @FXML
    ListView<String> followingListView;

    @FXML
    ListView<String> newsFeedListView;

    @FXML
    private Label myLabel;

    String userID;
    User user;
    UserFunction userfunction;

    UserManager usermanager = UserManager.getInstance();

    // Initial setup, could be used for setting default states or loading user info.
    @FXML
    public void initialize() {
        updateLastUpdate("Last updated: ");
    }

    // Follows a user based on the input in the followTextField.
    public void followUser() {
        try {
            userfunction.follow(followTextField.getText());
            if (usermanager.getUserMap().containsKey(followTextField.getText())) {
                setFollowingList(user.getFollowing());
            }
        } catch (NoUserException e) {
            System.err.println("Could not find user: " + e.getMessage());
        }
        followTextField.setText("");
    }

    // Posts a tweet and updates the news feed accordingly.
    public void postTweet() {
        try {
            userfunction.tweet(tweetTextField.getText());
            setNewsFeed(user.getNewsFeed());
        } catch (NoUserException e) {
            e.printStackTrace();
        }
        tweetTextField.setText("");
    }

    // Sets the user for this control, enabling user-specific actions.
    public void setUser(String id) {
        try {
            user = usermanager.getUserByID(id);
            userfunction = new UserFunction(user);
        } catch (NoUserException e) {
            System.err.println("Could not retrieve user" + e.getMessage());
        }
    }

    // Updates the list of users that the current user is following.
    public void setFollowingList(List<String> following) {
        ObservableList<String> items = FXCollections.observableArrayList(following);
        followingListView.setItems(items);

    }

    // Updates the news feed with tweets from followed users.
    public void setNewsFeed(Map<String, List<String>> newsFeed) {
        List<String> feedItems = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : newsFeed.entrySet()) {
            String userId = entry.getKey();
            List<String> tweets = entry.getValue();
            for (String tweet : tweets) {
                feedItems.add(userId + ": " + tweet); // Formats each tweet with the user ID
            }
        }
        ObservableList<String> items = FXCollections.observableArrayList(feedItems);
        newsFeedListView.setItems(items);
    }

    // Ensures the UI is updated with the latest news feed info in response to new tweets.
    public void updateNewsFeedUI() {
        Platform.runLater(() -> {
            setNewsFeed(user.getNewsFeed());
        });
    }

    // Updates a label, perhaps with the last time the news feed was updated.
    public void updateLastUpdate(String text) {
        myLabel.setText(text);
    }

}
