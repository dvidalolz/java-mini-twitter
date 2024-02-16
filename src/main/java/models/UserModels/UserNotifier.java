package main.java.models.UserModels;

import java.util.ArrayList;
import java.util.List;

import main.java.exceptions.DuplicateIDException;
import main.java.exceptions.NoUserException;
import main.java.models.interfaces.Observer;
import main.java.models.interfaces.Subject;


/**
 * Handles notification logic for user updates, managing a list of observers interested in such updates.
 */
public class UserNotifier implements Subject {
    private UserManager userManager;
    private List<Observer> observers;

    public UserNotifier() {
        this.userManager = UserManager.getInstance();
        this.observers = new ArrayList<>();
    }

    /**
     * Registers an observer to be notified of updates, ensuring no duplicates.
     */
    @Override
    public void registerObserver(Observer observer) throws DuplicateIDException {
        if (observers.contains(observer)) {
            throw new DuplicateIDException("Attempted add duplicate Observer");
        } else {
            observers.add(observer);
        }
    }

    /**
     * Removes a previously registered observer.
     */
    @Override
    public void removeObserver(Observer observer) throws NoUserException {
        if (observers.contains(observer)) {
            throw new NoUserException("Attempted delete non-existent Observer");
        } else {
            observers.remove(observer);
        }
    }

    /**
     * Notifies all registered observers of a new tweet by a user.
     */
    @Override
    public void notifyObservers(String userID, String tweet) throws NoUserException {
        for (Observer observer : observers) {
            try {
                observer.updateNewsFeed(userID, tweet);
            } catch (NoUserException e) {
                System.err.println("Failed to notify observer: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
    * Initializes the list of observers based on a user's followers, preparing for notification.
    */
    public void initObservers(User user) {
        observers.clear();
        for (String followerID : user.getFollowers()) {
            try {
                Observer follower = userManager.getUserByID(followerID);
                observers.add(follower);
            } catch (NoUserException e) {
                System.err.println("Failed to initialize observer for user ID: " + followerID);
                e.printStackTrace();
            }
        }
    }

}
