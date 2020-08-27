package com.unishaala.rest.repository;

import com.unishaala.rest.model.ClassDO;
import com.unishaala.rest.model.SessionDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SessionRepository extends PagingAndSortingRepository<SessionDO, UUID> {
    Page<SessionDO> findByAClass(final ClassDO classDO, final Pageable pageable);
}
