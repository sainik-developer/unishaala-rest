package com.unishaala.rest.service;

import com.unishaala.rest.model.Otp;
import com.unishaala.rest.repository.OtpRedisRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final OtpRedisRepo otpRedisRepo;
    private final OtpService otpService;
    private final SmsService smsService;

    public void sendOtp(final String phoneNumber) {
        final Otp otp = Otp.builder().phoneNumber(phoneNumber).otp(otpService.generateOtp()).build();
        smsService.sendSms(phoneNumber, otp.getOtp());
        otpRedisRepo.save(otp);
    }
}
