package main.java.models.interfaces;

import main.java.models.UserModels.UserManager;

public interface AnalyticsVisitor {
    void visit(UserManager userManager);
    int getTotal();
}
