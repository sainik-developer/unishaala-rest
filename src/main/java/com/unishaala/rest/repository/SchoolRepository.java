package com.unishaala.rest.repository;

import com.unishaala.rest.model.SchoolDO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SchoolRepository extends PagingAndSortingRepository<SchoolDO, UUID> {
    SchoolDO findByName(final String name);
}
