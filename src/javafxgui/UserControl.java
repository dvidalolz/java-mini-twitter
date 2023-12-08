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

public class UserControl {
    @FXML
    TextField followTextField;

    @FXML
    TextField tweetTextField;

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

    @FXML
    public void initialize() {
        updateLastUpdate("Last updated: ");
    }

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

    public void postTweet() {
        try {
            userfunction.tweet(tweetTextField.getText());
            setNewsFeed(user.getNewsFeed());
        } catch (NoUserException e) {
            e.printStackTrace();
        }
        tweetTextField.setText("");
    }

    public void setUser(String id) {
        try {
            user = usermanager.getUserByID(id);
            userfunction = new UserFunction(user);
        } catch (NoUserException e) {
            System.err.println("Could not retrieve user" + e.getMessage());
        }
    }

    public void setFollowingList(List<String> following) {
        ObservableList<String> items = FXCollections.observableArrayList(following);
        followingListView.setItems(items);

    }

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

    public void updateNewsFeedUI() {
        Platform.runLater(() -> {
            setNewsFeed(user.getNewsFeed());
        });
    }

    public void updateLastUpdate(String text) {
        myLabel.setText(text);
    }

}
