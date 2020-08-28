package com.unishaala.rest.repository;

import com.unishaala.rest.model.SchoolDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SchoolRepository extends PagingAndSortingRepository<SchoolDO, UUID> {
    SchoolDO findByName(final String name);

    Page<SchoolDO> findByNameContaining(final String name, Pageable pageable);
}
