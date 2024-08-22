package com.mynt.parcel_management.rest;

import com.mynt.parcel_management.library.RestModel;
import com.mynt.parcel_management.library.Voucher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class VoucherRetriever extends RestModel {
    private final Logger log = LogManager.getLogger(VoucherRetriever.class);

    public Voucher getVoucherWithRetry(String voucher) throws Exception {
        Voucher response = null;
        voucher = URLEncoder.encode(voucher, StandardCharsets.UTF_8);
        int retryCount = 0;
        while (retryCount <= this.getMaxRetry()) {
            try {
                if (retryCount == 2) {
                    log.error("Retry count: " + retryCount + ", retrying to retrieve voucher");
                }
                response = getVoucher(voucher);
            } catch (Exception e) {
                if (retryCount >= this.getMaxRetry()) {
                    throw e;
                }
                retryCount++;
                log.error(e.getMessage() + " Failed fetching voucer after several retries.");
            }
        }
        return response;
    }

    private Voucher getVoucher(String voucher) throws IOException {

        if (StringUtils.isEmpty(voucher)) {
            log.error("Cannot retrieve Voucher object because voucher is empty");
        }

        if (this.getRestUrl() == null) {
            throw new IOException("Base URL is null");
        }

        RestClient restClient = RestClient.builder()
                .baseUrl(this.getRestUrl() + "/voucher/" + voucher + "/?key=apikey")
                .defaultHeader("Content-Type", "application/json")
                .build();

        ResponseEntity<Voucher> response = restClient.get().retrieve().toEntity(Voucher.class);

        if ((response.getStatusCode().value() < 200) || (response.getStatusCode().value() >= 300)) {
            log.error("ERROR: Status code: " + response.getStatusCode());
            throw new RuntimeException("ERROR: Status code: " + response.getStatusCode());
        }

        return response.getBody();
    }
}
