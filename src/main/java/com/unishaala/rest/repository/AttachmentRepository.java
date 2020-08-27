package com.unishaala.rest.repository;

import com.unishaala.rest.model.AttachmentDO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AttachmentRepository extends PagingAndSortingRepository<AttachmentDO, UUID> {
}
