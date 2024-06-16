package com.example.invext.infra.database.mysql.repository;

import com.example.invext.domain.customerservicecenter.enumeration.Department;
import com.example.invext.infra.database.mysql.entity.AttendantJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AttendantJpaRepository extends JpaRepository<AttendantJpaEntity, String> {

  @Query("SELECT a FROM Attendant a WHERE a.department = :department AND SIZE(a.ticketList) < :lessThan")
  Optional<AttendantJpaEntity> findByDepartmentAndNumberOfTicketsLessThan(
      @Param("department") Department department,
      @Param("lessThan") Integer lessThan
  );
}
