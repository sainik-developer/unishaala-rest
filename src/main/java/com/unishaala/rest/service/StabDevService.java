package com.unishaala.rest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(value = "stage")
@RequiredArgsConstructor
public class StabDevService implements SmsService, OtpService {
    @Override
    public boolean sendSms(String phoneNumber, String message) {
        // stab implementation which will send no sms
        return true;
    }

    @Override
    public String generateOtp() {
        return "111111";
    }
}
