package com.unishaala.rest.repository;

import com.unishaala.rest.model.Otp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRedisRepo extends CrudRepository<Otp, String> {

}
