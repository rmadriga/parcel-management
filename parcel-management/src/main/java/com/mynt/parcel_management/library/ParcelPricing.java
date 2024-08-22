package com.mynt.parcel_management.library;

public class ParcelPricing {
    private double heavyParcelCosts;
    private double smallParcelCosts;
    private double mediumParcelCosts;
    private double largeParcelCosts;

    public ParcelPricing() {
        this.heavyParcelCosts = 20;
        this.smallParcelCosts = 0.03;
        this.mediumParcelCosts = 0.04;
        this.largeParcelCosts = 0.05;
    }

    public double getHeavyParcelCosts() {
        return heavyParcelCosts;
    }

    public void setHeavyParcelCosts(double heavyParcelCosts) {
        this.heavyParcelCosts = heavyParcelCosts;
    }

    public double getSmallParcelCosts() {
        return smallParcelCosts;
    }

    public void setSmallParcelCosts(double smallParcelCosts) {
        this.smallParcelCosts = smallParcelCosts;
    }

    public double getMediumParcelCosts() {
        return mediumParcelCosts;
    }

    public void setMediumParcelCosts(double mediumParcelCosts) {
        this.mediumParcelCosts = mediumParcelCosts;
    }

    public double getLargeParcelCosts() {
        return largeParcelCosts;
    }

    public void setLargeParcelCosts(double largeParcelCosts) {
        this.largeParcelCosts = largeParcelCosts;
    }
}
