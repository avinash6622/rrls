package com.unify.rrls.repository;

import com.unify.rrls.domain.DeleteNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface DeleteNotificationRepository extends JpaRepository<DeleteNotification,Integer> {
}
