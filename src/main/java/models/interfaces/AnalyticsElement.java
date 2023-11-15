package main.java.models.interfaces;

public interface AnalyticsElement {
    void accept(AnalyticsVisitor visitor);
}
