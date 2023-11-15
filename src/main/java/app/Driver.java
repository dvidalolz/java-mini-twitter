package main.java.app;

import main.java.exceptions.DuplicateIDException;
import main.java.exceptions.NoUserException;
import main.java.models.UserModels.User;
import main.java.models.UserModels.UserFunction;
import main.java.models.UserModels.UserManager;

public class Driver {

    public static void main(String[] args) throws NoUserException, DuplicateIDException {
        UserManager userManager = UserManager.getInstance();

        // created users
        userManager.createUser("Suzy");
        userManager.createUser("David");
        userManager.createUser("Teddy");

        // these are the users
        User suzy = userManager.getUserByID("Suzy");
        User david = userManager.getUserByID("David");
        User teddy = userManager.getUserByID("Teddy");

        // these are user functions
        UserFunction suzyUserFunction = new UserFunction(suzy);
        UserFunction davidUserFunction = new UserFunction(david);
        UserFunction teddyUserFunction = new UserFunction(teddy);

        // suzy follows david and Teddy
        suzyUserFunction.follow("David");
        suzyUserFunction.follow("Teddy");

        // lets make david and teddy tweet stuff
        davidUserFunction.tweet("I e");
        davidUserFunction.tweet("Eggs and Bacon");
        davidUserFunction.tweet("pes");
        davidUserFunction.tweet("r");
        davidUserFunction.tweet("cink");

        teddyUserFunction.tweet("gimme food");
        teddyUserFunction.tweet("flick ma ck");
        teddyUserFunction.tweet("imma steal ur socks");

        System.out.println(suzy.getNewsFeed());
        System.out.println(userManager.getUserGroupByID("Root"));


    }
}
