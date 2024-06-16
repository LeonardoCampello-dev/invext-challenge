package com.example.invext.domain.customerservicecenter.service;

import com.example.invext.domain.customerservicecenter.entity.Attendant;
import com.example.invext.domain.customerservicecenter.enumeration.Department;
import com.example.invext.domain.customerservicecenter.repository.IAttendantRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class TicketOpeningService {
  private final IAttendantRepository attendantRepository;

  public Optional<Attendant> findAvailableAttendant(Department department) {
    return attendantRepository.findByDepartmentAndNumberOfTicketsLessThan(
        department,
        3
    );
  }
}
