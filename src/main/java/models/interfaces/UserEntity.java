package main.java.models.interfaces;

import java.time.LocalTime;


/**
 * Interface defining a user entity in the system, providing a common method to retrieve the entity's ID.
 * It includes a timeCreated field, initialized with the current time, to track when the entity was created.
 */
public interface UserEntity {
    LocalTime timeCreated = LocalTime.now();
    String getID();
}
