package javafxgui;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import main.java.exceptions.DuplicateIDException;
import main.java.exceptions.NoUserException;
import main.java.models.UserModels.UserManager;
import main.java.models.UserModels.UserGroup;
import main.java.models.AnalyticsModels.LastUpdateTime;
import main.java.models.AnalyticsModels.PositiveTweetTotal;
import main.java.models.AnalyticsModels.TweetTotal;
import main.java.models.AnalyticsModels.UserGroupTotalVisitor;
import main.java.models.AnalyticsModels.UserTotalVisitor;
import main.java.models.UserModels.User;
import main.java.models.interfaces.UserEntity;
import javafx.scene.control.TreeCell;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AdminControl implements Initializable {
    @FXML
    private TextField userTextField;
    @FXML
    private TextField groupTextField;
    @FXML
    private TreeView<UserEntity> treeView;

    UserManager usermanager = UserManager.getInstance();

    public void addUser(ActionEvent e) {
        String userID = userTextField.getText();
        TreeItem<UserEntity> selectedItem = selectItem();

        if (selectedItem != null && selectedItem.getValue() instanceof UserGroup) {
            UserGroup parentGroup = (UserGroup) selectedItem.getValue();
            try {
                parentGroup.addUserEntity(userID);
            } catch (DuplicateIDException error) {
                System.err.println("Could not create user: " + error.getMessage());
            }
            addUserToTree(selectedItem, userID);

        } else {
            System.err.println("Error: Items can only be added under groups. Please select a group.");
        }
        userTextField.setText("");

    }

    public void addGroup(ActionEvent e) {
        String groupID = groupTextField.getText();
        TreeItem<UserEntity> selectedItem = selectItem();

        if (selectedItem != null && selectedItem.getValue() instanceof UserGroup) {
            // group of selected tree item
            UserGroup parentGroup = (UserGroup) selectedItem.getValue();
            try {
                parentGroup.addUserEntity(groupID);
            } catch (DuplicateIDException error) {
                System.err.println("Could not create user: " + error.getMessage());
            }
            addGroupToTree(selectedItem, groupID);
        } else {
            System.err.println("Error: Items can only be added under groups. Please select a group.");
        }
        groupTextField.setText("");
    }

    public void openUserView(ActionEvent e) throws IOException {
        TreeItem<UserEntity> selectedItem = selectItem();

        if (selectedItem != null && selectedItem.getValue() instanceof User) {
            // load fxml and create new scene for popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userpanel.fxml"));
            Parent newRoot = loader.load();

            // create new stage, new window
            Stage newStage = new Stage();
            Scene newScene = new Scene(newRoot);

            newStage.setScene(newScene);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String formattedTime = selectedItem.getValue().timeCreated.format(formatter);

            newStage.setTitle(
                    "User " + selectedItem.getValue().getID() + " created at: " + formattedTime);

            // give selected userID to user controller
            UserControl uc = loader.getController();
            User selectedUser = (User) selectedItem.getValue();
            uc.setUser(selectedUser.getID());

            selectedUser.setUserControl(uc);

            List<String> following = selectedUser.getFollowing();
            Map<String, List<String>> newsFeed = selectedUser.getNewsFeed();

            if (!following.isEmpty()) {
                uc.setFollowingList(following);
            }

            if (!newsFeed.isEmpty()) {
                uc.setNewsFeed(newsFeed);
            }

            newStage.show();
        } else {
            System.err.println("Must select user to open user view.");
        }
    }

    public void showUserTotal(ActionEvent e) throws IOException {
        // analytics
        UserTotalVisitor userAnalytics = new UserTotalVisitor();
        userAnalytics.visit(usermanager);

        // load fxml and create new scene for popup
        FXMLLoader loader = new FXMLLoader(getClass().getResource("usertotal.fxml"));
        Parent newRoot = loader.load();

        // create new stage, new window
        Stage newStage = new Stage();
        Scene newScene = new Scene(newRoot);

        newStage.setScene(newScene);
        newStage.setTitle("User Total");

        // use controller to display analytics total
        AnalyticsControl ac = loader.getController();
        ac.displayTotal("User", userAnalytics.getTotal());

        newStage.show();

    }

    public void showGroupTotal(ActionEvent e) throws IOException {
        // analytics
        UserGroupTotalVisitor userGroupAnalytics = new UserGroupTotalVisitor();
        userGroupAnalytics.visit(usermanager);

        // load fxml and create new scene for popup
        FXMLLoader loader = new FXMLLoader(getClass().getResource("usergrouptotal.fxml"));
        Parent newRoot = loader.load();

        // create new stage, new window
        Stage newStage = new Stage();
        Scene newScene = new Scene(newRoot);

        newStage.setScene(newScene);
        newStage.setTitle("User Group Total");

        // use controller to display analytics total
        AnalyticsControl ac = loader.getController();
        ac.displayTotal("Group", userGroupAnalytics.getTotal());

        newStage.show();
    }

    public void showMessageTotal(ActionEvent e) throws IOException {
        // analytics
        TweetTotal tweetAnalytics = new TweetTotal();
        tweetAnalytics.visit(usermanager);

        // load fxml and create new scene for popup
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tweettotal.fxml"));
        Parent newRoot = loader.load();

        // create new stage, new window
        Stage newStage = new Stage();
        Scene newScene = new Scene(newRoot);

        newStage.setScene(newScene);
        newStage.setTitle("Tweet Total");

        // use controller to display analytics total
        AnalyticsControl ac = loader.getController();
        ac.displayTotal("Tweet", tweetAnalytics.getTotal());

        newStage.show();
    }

    public void showPositiveTotal(ActionEvent e) throws IOException {
        // analytics
        PositiveTweetTotal posTweetAnalytics = new PositiveTweetTotal();
        posTweetAnalytics.visit(usermanager);

        // load fxml and create new scene for popup
        FXMLLoader loader = new FXMLLoader(getClass().getResource("postweettotal.fxml"));
        Parent newRoot = loader.load();

        // create new stage, new window
        Stage newStage = new Stage();
        Scene newScene = new Scene(newRoot);

        newStage.setScene(newScene);
        newStage.setTitle("Positive Tweet Total");

        // use controller to display analytics total
        AnalyticsControl ac = loader.getController();
        ac.displayTotal("Positive Tweet", posTweetAnalytics.getTotal());

        newStage.show();
    }

    public void showLastUpdateTime(ActionEvent e) throws IOException {
        // analytics
        LastUpdateTime lastUpdateTime = new LastUpdateTime();
        lastUpdateTime.visit(usermanager);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("lastupdatetime.fxml"));
        Parent newRoot = loader.load();

        Stage newStage = new Stage();
        Scene newScene = new Scene(newRoot);

        newStage.setScene(newScene);
        newStage.setTitle("Last Update Time");

        AnalyticsControl ac = loader.getController();
        String text = convertIntToTime(lastUpdateTime.getTotal());
        ac.displayTotal("Last update at: " + text, 1);

        newStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            UserEntity rootGroup = usermanager.getUserGroupByID("Root");
            TreeItem<UserEntity> rootItem = new TreeItem<>(rootGroup);
            treeView.setRoot(rootItem);
        } catch (NoUserException error2) {
            System.err.println("Could not retrieve group: " + error2.getMessage());
        }

        treeView.setCellFactory(tv -> new TreeCell<UserEntity>() {
            @Override
            protected void updateItem(UserEntity item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.toString());
                    if (item instanceof UserGroup) {
                        setFont(Font.font("System", FontWeight.BOLD, 12));
                    } else {
                        setFont(Font.font("System", FontWeight.NORMAL, 12));
                    }
                }
            }
        });

    }

    // return selected treeItem, if none, return the root.
    public TreeItem<UserEntity> selectItem() {
        TreeItem<UserEntity> item = treeView.getSelectionModel().getSelectedItem();
        if (item != null) {
            return item;
        } else {
            return treeView.getRoot();
        }

    }

    private void addUserToTree(TreeItem<UserEntity> selectedItem, String ID) {
        try {
            usermanager.createUser(ID);
            User newUser = usermanager.getUserByID(ID);
            TreeItem<UserEntity> newUserItem = new TreeItem<>(newUser);
            selectedItem.getChildren().add(newUserItem);
            selectedItem.setExpanded(true);
        } catch (DuplicateIDException error) {
            System.err.println("Could not create user: " + error.getMessage());
        } catch (NoUserException error2) {
            System.err.println("Could not retrieve user: " + error2.getMessage());
        }

    }

    private void addGroupToTree(TreeItem<UserEntity> selectedItem, String ID) {
        try {
            usermanager.createUserGroup(ID);
            UserGroup newUserGroup = usermanager.getUserGroupByID(ID);
            TreeItem<UserEntity> newUserGroupItem = new TreeItem<>(newUserGroup);
            selectedItem.getChildren().add(newUserGroupItem);
            selectedItem.setExpanded(true);
        } catch (DuplicateIDException error) {
            System.err.println("Could not create user: " + error.getMessage());
        } catch (NoUserException error2) {
            System.err.println("Could not retrieve user: " + error2.getMessage());
        }

    }

    private static String convertIntToTime(int totalMinutes) {
        int hours = totalMinutes / 60; // Get the whole hours
        int minutes = totalMinutes % 60; // Get the remaining minutes

        return String.format("%02d:%02d", hours, minutes);
    }

}
