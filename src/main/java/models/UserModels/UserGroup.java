package main.java.models.UserModels;

import java.util.ArrayList;
import java.util.List;

import main.java.exceptions.DuplicateIDException;
import main.java.models.interfaces.UserEntity;

public class UserGroup implements UserEntity {
    private String userGroupID;
    private List<String> userEntities;

    public UserGroup(String userGroupID) {
        this.userGroupID = userGroupID;
        userEntities = new ArrayList<>();
    }

    @Override
    public String getID() {
        return userGroupID;
    }

    public List<String> getUserEntities() {
        return userEntities;
    }

    public void addUserEntity(String entityID) throws DuplicateIDException {
        if (userEntities.contains(entityID)) {
            throw new DuplicateIDException("User Entity ID" + entityID + "already exists in the list");
        } else {
            userEntities.add(entityID);
        }
    }

    // override toString() method for treeView to return id string
    @Override
    public String toString() {
        return userGroupID;
    }

}
