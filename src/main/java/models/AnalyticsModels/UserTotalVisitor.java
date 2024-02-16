package main.java.models.AnalyticsModels;

import main.java.models.UserModels.UserManager;
import main.java.models.interfaces.AnalyticsVisitor;


/**
 * Counts the total number of users.
 */
public class UserTotalVisitor implements AnalyticsVisitor{
    
    private int userTotal;
    
    /**
     * Visits the UserManager to count the number of users.
     * 
     * @param userManager The source of user data.
     */
    @Override
    public void visit(UserManager userManager) {
        userTotal = userManager.getUserMap().size();
    }

    @Override
    public int getTotal() {
        return userTotal;
    }
    
}
