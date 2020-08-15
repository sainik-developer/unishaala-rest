package com.unishaala.rest.service;

public interface SmsService {
    boolean sendSms(final String phoneNumber, final String otp);
}
