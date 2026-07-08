package com.innovatewithomer.multiuserrestapi.repository;

import com.innovatewithomer.multiuserrestapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByUserId(Long userId);
    boolean existsByIdAndUserId(Long id, Long userId);
    int countByUserId(Long userId);
}