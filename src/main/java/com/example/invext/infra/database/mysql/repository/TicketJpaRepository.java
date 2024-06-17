package com.example.invext.infra.database.mysql.repository;

import com.example.invext.infra.database.mysql.entity.TicketJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketJpaRepository extends JpaRepository<TicketJpaEntity, Integer> {
}
