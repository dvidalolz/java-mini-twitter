package main.java.models.interfaces;

import main.java.exceptions.DuplicateIDException;
import main.java.exceptions.NoUserException;

public interface Subject {
    void registerObserver(Observer observer) throws DuplicateIDException;

    void removeObserver(Observer observer) throws NoUserException;

    void notifyObservers(String userID, String tweet) throws NoUserException;
}
