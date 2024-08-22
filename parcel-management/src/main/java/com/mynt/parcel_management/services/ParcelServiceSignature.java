package com.mynt.parcel_management.services;

import com.mynt.parcel_management.library.Parcel;
import com.mynt.parcel_management.library.ParcelPricing;
import com.mynt.parcel_management.library.ResponseMessage;

public interface ParcelServiceSignature {
    public ResponseMessage compute(Parcel parcel, ParcelPricing pricing);
}
