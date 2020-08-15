package com.unishaala.rest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/***
 *
 */
@Log4j2
@Service
@Profile(value = "prod")
@RequiredArgsConstructor
public class TextLocalProdService implements SmsService,OtpService {
    private final RestTemplate restTemplate;

    @Value("{$(textlocal.sms.url)}")
    private String TEXT_LOCAL_SMS_SEND_URL;
    @Value("{$(textlocal.sms.template)}")
    private String TEXT_LOCAL_TEMPLATE;
    @Value("{$(textlocal.sms.sender)}")
    private String TEXT_LOCAL_SENDER;
    @Value("{$(textlocal.sms.apikey)}")
    private String TEXT_LOCAL_API_KEY;

    @Override
    public boolean sendSms(String phoneNumber, String otp) {
        log.info("Sms is going to be send to {} using service text local and sms content is : {}", phoneNumber, String.format(TEXT_LOCAL_TEMPLATE, otp));
        final ResponseEntity<String> textLocalResponseEntity =
                restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(TEXT_LOCAL_SMS_SEND_URL)
                                .queryParam("apikey", TEXT_LOCAL_API_KEY)
                                .queryParam("message", String.format(TEXT_LOCAL_TEMPLATE, otp))
                                .queryParam("sender", TEXT_LOCAL_SENDER)
                                .queryParam("numbers", phoneNumber).build().toUriString(),
                        HttpMethod.POST, createRequest(), String.class);
        log.info("Text local SMS sending response is {}", textLocalResponseEntity.getBody());
        return true;
    }

    public String generateOtp(){
        return String.valueOf((int) Math.floor(100000 + Math.random() * 900000));
    }

    private HttpEntity<?> createRequest() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        return new HttpEntity<>(null, headers);
    }
}
