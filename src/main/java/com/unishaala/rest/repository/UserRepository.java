package com.unishaala.rest.repository;

import com.unishaala.rest.enums.UserType;
import com.unishaala.rest.model.ClassDO;
import com.unishaala.rest.model.UserDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserDO, UUID> {
    UserDO findByMobileNumberAndUserType(final String mobileNumber, final UserType userType);

    UserDO findByIdAndMobileNumberAndUserType(final UUID userId, final String mobileNumber, final UserType userType);

    long countByRelatedClass(final ClassDO classDo);

    List<UserDO> findByRelatedClass(final ClassDO classDo, Pageable pageable);

    Page<UserDO> findByUserNameContainingAndUserType(final String userName, final UserType userType, Pageable pageable);
}
