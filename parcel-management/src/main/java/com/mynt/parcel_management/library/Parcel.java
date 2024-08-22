package com.mynt.parcel_management.library;

import com.mynt.parcel_management.lov.RuleName;

public class Parcel {

    private double weight;
    private double height;
    private double length;
    private double width;
    private double volume;
    private RuleName rule;
    private double deliveryFee;

    private Voucher voucher;

    public Parcel(double weight, double height, double length, double width) {
        this.weight = weight;
        this.height = height;
        this.length = length;
        this.width = width;
        if (height > 0 && width > 0 && length > 0) {
            this.volume = height * width * length;
        }

    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public RuleName getRule() {
        return rule;
    }

    public void setRule(RuleName rule) {
        this.rule = rule;
    }
    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    @Override
    public String toString() {
        return "Parcel{" +
                "weight=" + weight +
                ", height=" + height +
                ", length=" + length +
                ", width=" + width +
                ", volume=" + volume +
                ", rule=" + rule +
                ", deliveryFee=" + deliveryFee +
                ", voucher=" + voucher +
                '}';
    }
}
