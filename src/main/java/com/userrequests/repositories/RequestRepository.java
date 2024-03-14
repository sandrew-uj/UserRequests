package com.userrequests.repositories;

import com.userrequests.models.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    @Query("select r from Request r join r.userEntity where r.userEntity.username like %:username%")
    Page<Request> findAllUsernameContaining(String username, Pageable pageable);

    @Query("select r from Request r join r.userEntity where r.userEntity.username = :username")
    Page<Request> findAllByUsername(String username, Pageable pageable);

    @Query("select r from Request r where r.status = :status")
    Page<Request> findAllByStatus(String status, Pageable pageable);

    @Query("select r from Request r join r.userEntity where r.userEntity.username = :username and r.status = :status")
    Page<Request> findAllByUsernameAndStatus(String username, String status, Pageable pageable);

    @Query("select r from Request r join r.userEntity where r.userEntity.username like %:username% and r.status = :status")
    Page<Request> findAllUsernameContainingAndStatus(String username, String status, Pageable pageable);
}
