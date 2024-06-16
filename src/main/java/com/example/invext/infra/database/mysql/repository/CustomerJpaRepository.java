package com.example.invext.infra.database.mysql.repository;

import com.example.invext.infra.database.mysql.entity.CustomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, String> {
}
