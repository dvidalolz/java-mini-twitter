package main.java.models.AnalyticsModels;

import main.java.models.UserModels.UserManager;
import main.java.models.interfaces.AnalyticsVisitor;

public class UserTotalVisitor implements AnalyticsVisitor{
    
    private int userTotal;
    
    @Override
    public void visit(UserManager userManager) {
        userTotal = userManager.getUserMap().size();
    }

    @Override
    public int getTotal() {
        return userTotal;
    }
    
}
