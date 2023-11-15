package main.java.models.AnalyticsModels;

import main.java.models.UserModels.UserManager;
import main.java.models.interfaces.AnalyticsVisitor;

public class UserGroupTotalVisitor implements AnalyticsVisitor {

    private int userGroupTotal;

    @Override
    public void visit(UserManager userManager) {
        userGroupTotal = userManager.getUserGroupMap().size();
    }

    @Override
    public int getTotal() {
        return userGroupTotal;
    }
    
}
