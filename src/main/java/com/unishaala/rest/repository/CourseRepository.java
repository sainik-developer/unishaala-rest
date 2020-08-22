package com.unishaala.rest.repository;

import com.unishaala.rest.model.CourseDO;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface CourseRepository extends PagingAndSortingRepository<CourseDO, UUID> {
}
