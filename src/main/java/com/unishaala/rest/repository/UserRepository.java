package com.unishaala.rest.repository;

import com.unishaala.rest.enums.UserType;
import com.unishaala.rest.model.UserDO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserDO, UUID> {
    UserDO findByMobileNumberAndUserType(final String mobileNumber, final UserType userType);

    UserDO findByIdAndMobileNumberAndUserType(final UUID userId, final String mobileNumber, final UserType userType);

}
