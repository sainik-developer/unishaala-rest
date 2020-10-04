package com.unishaala.rest.repository;

import com.unishaala.rest.model.CourseDO;
import com.unishaala.rest.model.UserDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends PagingAndSortingRepository<CourseDO, UUID> {
    List<CourseDO> findByTeacher(final UserDO teacher);

    Page<CourseDO> findByTeacher(final UserDO teacher, final Pageable pageable);
}
