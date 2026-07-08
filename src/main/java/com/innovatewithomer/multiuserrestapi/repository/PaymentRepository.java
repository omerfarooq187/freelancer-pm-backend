package com.innovatewithomer.multiuserrestapi.repository;

import com.innovatewithomer.multiuserrestapi.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByProjectId(Long projectId);
    List<Payment> findByProjectClientUserId(Long userId);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.project.id = :projectId AND p.status = 'PAID'")
    Double sumPaidByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.project.id = :projectId AND p.status = 'PENDING'")
    Double sumPendingByProjectId(@Param("projectId") Long projectId);
}
