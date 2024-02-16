package main.java.models.interfaces;

import main.java.exceptions.DuplicateIDException;
import main.java.exceptions.NoUserException;


/**
 * Defines the Subject interface for the Observer pattern.
 * This interface is used by objects that can have Observers attached to them, allowing for the notification of changes, such as new tweets.
 */
public interface Subject {

    /**
     * Registers an Observer to be notified of updates.
     * 
     * @param observer The Observer to be added.
     * @throws DuplicateIDException If the Observer is already registered and duplicates are not allowed.
     */
    void registerObserver(Observer observer) throws DuplicateIDException;

    /**
     * Removes an Observer, stopping it from receiving further updates.
     * 
     * @param observer The Observer to be removed.
     * @throws NoUserException If the specified Observer is not registered.
     */
    void removeObserver(Observer observer) throws NoUserException;

    /**
     * Notifies all registered Observers of a change, such as a new tweet.
     * 
     * @param userID The ID of the user who posted the tweet.
     * @param tweet The content of the tweet to be broadcasted to Observers.
     * @throws NoUserException If the update process encounters an issue related to user identification.
     */
    void notifyObservers(String userID, String tweet) throws NoUserException;
}
