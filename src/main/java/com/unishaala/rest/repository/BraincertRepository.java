package com.unishaala.rest.repository;

import com.unishaala.rest.model.BraincertDO;
import com.unishaala.rest.model.SessionDO;
import com.unishaala.rest.model.UserDO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BraincertRepository extends PagingAndSortingRepository<BraincertDO, UUID> {
    BraincertDO findByUserAndSession(final UserDO userDO, final SessionDO sessionDO);
}
