package com.example.invext.domain.customerservicecenter.repository;

import com.example.invext.domain.customerservicecenter.entity.Attendant;
import com.example.invext.domain.customerservicecenter.enumeration.Department;

import java.util.Optional;

public interface IAttendantRepository {
  Optional<Attendant> findFirstByDepartmentAndNumberOfTicketsLessThanThree(
      Department department
  );
}
