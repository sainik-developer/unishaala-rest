package com.unishaala.rest.repository;

import com.unishaala.rest.model.ClassDO;
import com.unishaala.rest.model.SchoolDO;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ClassRepository extends PagingAndSortingRepository<ClassDO, UUID> {
    ClassDO findByNameAndSchool(final String name, final SchoolDO schoolDO);
}
