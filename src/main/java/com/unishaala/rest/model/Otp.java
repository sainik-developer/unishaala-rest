package com.unishaala.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Data
@Builder
@RedisHash("otp")
@AllArgsConstructor
@NoArgsConstructor
public class Otp {
    private String id;
    private String otp;
    @TimeToLive
    private Long timeout = 30L;
}
