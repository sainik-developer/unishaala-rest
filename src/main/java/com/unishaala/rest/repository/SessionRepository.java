package com.unishaala.rest.repository;

import com.unishaala.rest.model.SessionDO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionRepository extends PagingAndSortingRepository<SessionDO, UUID> {
}
