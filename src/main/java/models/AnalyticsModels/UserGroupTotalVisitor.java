package main.java.models.AnalyticsModels;

import main.java.models.UserModels.UserManager;
import main.java.models.interfaces.AnalyticsVisitor;



/**
 * Counts the total number of user groups.
 */
public class UserGroupTotalVisitor implements AnalyticsVisitor {

    private int userGroupTotal;

    /**
     * Visits the UserManager to count the number of user groups.
     * 
     * @param userManager The source of user group data.
     */
    @Override
    public void visit(UserManager userManager) {
        userGroupTotal = userManager.getUserGroupMap().size();
    }

    @Override
    public int getTotal() {
        return userGroupTotal;
    }
    
}
