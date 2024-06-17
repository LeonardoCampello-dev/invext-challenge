package com.example.invext.infra.database.mysql.repository;

import com.example.invext.domain.customerservicecenter.enumeration.Department;
import com.example.invext.infra.database.mysql.entity.AttendantJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttendantJpaRepository extends JpaRepository<AttendantJpaEntity, Integer> {

  @Query("SELECT a FROM AttendantJpaEntity a WHERE a.department = :department AND " +
      "(SELECT COUNT(t) FROM TicketJpaEntity t WHERE t.assignee = a AND t.status = 'IN_PROGRESS') < 3 " +
      "ORDER BY a.id ASC")
  Optional<AttendantJpaEntity> findFirstByDepartmentAndNumberOfTicketsLessThanThree(
      @Param("department") Department department
  );
}
