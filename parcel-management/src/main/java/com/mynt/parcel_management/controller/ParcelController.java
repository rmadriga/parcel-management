package com.mynt.parcel_management.controller;

import com.mynt.parcel_management.library.Parcel;
import com.mynt.parcel_management.library.ParcelPricing;
import com.mynt.parcel_management.library.ResponseMessage;
import com.mynt.parcel_management.library.Voucher;
import com.mynt.parcel_management.services.ParcelService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/parcel-management")
public class ParcelController {

    private final Logger log = LogManager.getLogger(ParcelController.class);
    ParcelPricing parcelPricing = new ParcelPricing();
    Voucher voucherDetails = new Voucher();

    @Autowired
    ParcelService parcelService;

    @RequestMapping(value = "/process/weight/{weight}/height/{height}/width/{width}/length/{length}")
    private ResponseMessage processParcel(@PathVariable double weight, @PathVariable double height, @PathVariable double width, @PathVariable double length) {
        log.info("Processing parcel . . .");
        Parcel parcel = new Parcel(weight, height, length, width);
        ResponseMessage rm = parcelService.compute(parcel, parcelPricing);
        parcel.setVoucher(voucherDetails);
        rm.setParcelPricing(parcelPricing);
        return rm;
    }

    @RequestMapping(value = "/pricing/heavy/{heavy}/large/{large}/medium/{medium}/small/{small}")
    private ResponseMessage setPricing(@PathVariable double heavy, @PathVariable double large, @PathVariable double medium, @PathVariable double small) {
        parcelPricing.setHeavyParcelCosts(heavy);
        parcelPricing.setLargeParcelCosts(large);
        parcelPricing.setMediumParcelCosts(medium);
        parcelPricing.setSmallParcelCosts(small);
        return parcelService.validatePricing(parcelPricing);
    }

    @RequestMapping(value = "/voucher/{voucher}")
    private String validateVoucher(@PathVariable String voucher) throws Exception {
        voucherDetails = parcelService.validateVoucher(voucher);
        return voucherDetails.getError();
    }
}
