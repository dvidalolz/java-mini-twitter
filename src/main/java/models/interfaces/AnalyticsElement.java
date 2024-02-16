package main.java.models.interfaces;

/**
 * Defines an element that can accept an AnalyticsVisitor for analytics calculations.
 */
public interface AnalyticsElement {
    void accept(AnalyticsVisitor visitor);
}
