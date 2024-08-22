package com.mynt.parcel_management.library;

import com.mynt.parcel_management.lov.ResponeMessageStatus;

public class ResponseMessage {

    private Parcel parcel;
    private ParcelPricing parcelPricing;
    private ResponeMessageStatus status;
    private String message;

    public Parcel getParcel() {
        return parcel;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }

    public ParcelPricing getParcelPricing() {
        return parcelPricing;
    }

    public void setParcelPricing(ParcelPricing parcelPricing) {
        this.parcelPricing = parcelPricing;
    }

    public ResponeMessageStatus getStatus() {
        return status;
    }

    public void setStatus(ResponeMessageStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
