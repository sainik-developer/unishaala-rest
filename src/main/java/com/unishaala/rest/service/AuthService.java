package com.unishaala.rest.service;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.unishaala.rest.exception.LoginException;
import com.unishaala.rest.exception.MobileNumberFormatException;
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
        final Otp otp = Otp.builder().id(phoneNumber).otp(otpService.generateOtp()).build();
        smsService.sendSms(phoneNumber, otp.getOtp());
        otpRedisRepo.save(otp);
    }

    public boolean verifyOtp(final String phoneNumber, final String otp) {
        return otpRedisRepo.findById(phoneNumber)
                .map(OTP -> OTP.getOtp().equals(otp))
                .orElseThrow(LoginException::new);
    }

    public String getFormattedNumber(final String phoneNumber) throws MobileNumberFormatException {
        try {
            return PhoneNumberUtil.getInstance().format(PhoneNumberUtil.getInstance().parse(phoneNumber, null),
                    PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (final NumberParseException e) {
            throw new MobileNumberFormatException("Incorrect Phone number provided: " + phoneNumber);
        }
    }
}
