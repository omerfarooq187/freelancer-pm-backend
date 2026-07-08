package com.innovatewithomer.multiuserrestapi.repository;

import com.innovatewithomer.multiuserrestapi.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByClientUserId(Long userId);
    List<Project> findByClientId(Long clientId);
    boolean existsByIdAndClientUserId(Long id, Long userId);
}
