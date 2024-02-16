package main.java.models.interfaces;

import main.java.models.UserModels.UserManager;


/**
 * Defines the interface for analytics visitors, enabling the implementation of various analytics calculations.
 * Visitors implementing this interface can perform operations on UserManager instances and retrieve calculated results.
 */
public interface AnalyticsVisitor {
    void visit(UserManager userManager);
    int getTotal();
}
