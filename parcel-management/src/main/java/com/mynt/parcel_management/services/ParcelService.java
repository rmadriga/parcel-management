package com.mynt.parcel_management.services;

import com.mynt.parcel_management.library.Parcel;
import com.mynt.parcel_management.library.ParcelPricing;
import com.mynt.parcel_management.library.ResponseMessage;
import com.mynt.parcel_management.library.Voucher;
import com.mynt.parcel_management.lov.ResponeMessageStatus;
import com.mynt.parcel_management.lov.RuleName;
import com.mynt.parcel_management.rest.VoucherRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class ParcelService implements ParcelServiceSignature {

    @Autowired
    VoucherRetriever voucherRetriever;

    public ResponseMessage compute(Parcel parcel, ParcelPricing pricing) {

        ResponseMessage rm = new ResponseMessage();
        double deliveryFeeCosts = 0;
        double discount = 0;

        identifyParcelRule(parcel); //Get the Rule Name

        switch (parcel.getRule()) {
            case REJECT -> {
                rm.setMessage("ERROR: Parcel weighs: " + String.valueOf(parcel.getWeight()) + ", it exceeds 50 kilograms.");
                rm.setStatus(ResponeMessageStatus.REJECTED);
            }
            case HEAVY -> deliveryFeeCosts = parcel.getWeight() * pricing.getHeavyParcelCosts();
            case SMALL -> deliveryFeeCosts = parcel.getVolume() * pricing.getSmallParcelCosts();
            case MEDIUM -> deliveryFeeCosts = parcel.getVolume() * pricing.getMediumParcelCosts();
            case LARGE -> deliveryFeeCosts = parcel.getVolume() * pricing.getLargeParcelCosts();
        }

        parcel.setDeliveryFee(deliveryFeeCosts);

        if (!ObjectUtils.isEmpty(parcel.getVoucher())) {
            discount = parcel.getDeliveryFee() * parcel.getVoucher().getDiscount();
            if ((parcel.getDeliveryFee() - discount) > 0) {
                parcel.setDeliveryFee(parcel.getDeliveryFee() - discount);
                rm.setMessage("Voucher discount applied: " + discount);
            }

        }

        rm.setParcel(parcel);
        if (!RuleName.REJECT.toString().equalsIgnoreCase(parcel.getRule().toString())) {
            if (parcel.getVoucher() != null && parcel.getVoucher().getDiscount() > 0) {
                rm.setMessage("SUCCESS: Delivery fee is: " + parcel.getDeliveryFee() + ". Voucher discount applied");
            } else {
                rm.setMessage("SUCCESS: Delivery fee is: " + parcel.getDeliveryFee());
            }

            rm.setStatus(ResponeMessageStatus.ACCEPTED);
        }

        return rm;
    }
    private void identifyParcelRule(Parcel parcel) {

        if (parcel.getWeight() > 50) {
            parcel.setRule(RuleName.REJECT);
        } else if (parcel.getWeight() <= 50 && parcel.getWeight() > 10) {
            parcel.setRule(RuleName.HEAVY);
        } else if (parcel.getVolume() < 1500) {
            parcel.setRule(RuleName.SMALL);
        } else if (parcel.getVolume() < 2500 && parcel.getVolume() >= 1500) {
            parcel.setRule(RuleName.MEDIUM);
        } else {
            parcel.setRule(RuleName.LARGE);
        }
    }

    public ResponseMessage validatePricing(ParcelPricing parcelPricing) {

        ResponseMessage rm = new ResponseMessage();
        rm.setParcel(null);

        if (parcelPricing.getHeavyParcelCosts() <= 0) {
            parcelPricing = new ParcelPricing();
            rm.setStatus(ResponeMessageStatus.INVALID);
            rm.setMessage("INVALID: Should not be zero/negative value. Pricing is in default value.");
            rm.setParcelPricing(parcelPricing);
            return rm;
        }

        if (parcelPricing.getLargeParcelCosts() <= 0) {
            parcelPricing = new ParcelPricing();
            rm.setStatus(ResponeMessageStatus.INVALID);
            rm.setMessage("INVALID: Should not be zero/negative value. Pricing is in default value.");
            rm.setParcelPricing(parcelPricing);
            return rm;
        }

        if (parcelPricing.getSmallParcelCosts() <= 0) {
            parcelPricing = new ParcelPricing();
            rm.setStatus(ResponeMessageStatus.INVALID);
            rm.setMessage("INVALID: Should not be zero/negative value. Pricing is in default value.");
            rm.setParcelPricing(parcelPricing);
            return rm;
        }

        if (parcelPricing.getMediumParcelCosts() <= 0) {
            parcelPricing = new ParcelPricing();
            rm.setStatus(ResponeMessageStatus.INVALID);
            rm.setMessage("INVALID: Should not be zero/negative value. Pricing is in default value.");
            rm.setParcelPricing(parcelPricing);
            return rm;
        }

        rm.setStatus(ResponeMessageStatus.ACCEPTED);
        rm.setMessage("SUCCESS: Prices has been set successfully");
        rm.setParcelPricing(parcelPricing);

        return rm;
    }

    public Voucher validateVoucher(String voucher) throws Exception {

        Voucher voucherDiscount = voucherRetriever.getVoucherWithRetry(voucher);

        if (ObjectUtils.isEmpty(voucherDiscount)) {
            return null;
        } else {
            if (voucherDiscount.getError() != null) {
                Voucher defaultVoucher = new Voucher();
                defaultVoucher.setError("ERROR: " + voucherDiscount.getError());
                return defaultVoucher;
            } else {
                voucherDiscount.setError("Success: Valid Voucher");
            }
        }

        return voucherDiscount;
    }
}
