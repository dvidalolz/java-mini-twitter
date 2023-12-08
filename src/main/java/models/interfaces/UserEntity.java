package main.java.models.interfaces;

import java.time.LocalTime;

public interface UserEntity {
    LocalTime timeCreated = LocalTime.now();
    String getID();
}
