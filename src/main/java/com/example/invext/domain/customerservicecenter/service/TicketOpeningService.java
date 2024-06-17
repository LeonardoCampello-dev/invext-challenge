package com.example.invext.domain.customerservicecenter.service;

import com.example.invext.domain.customerservicecenter.entity.Attendant;
import com.example.invext.domain.customerservicecenter.enumeration.Department;
import com.example.invext.domain.customerservicecenter.repository.IAttendantRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TicketOpeningService {
  @Autowired
  private final IAttendantRepository attendantRepository;

  public Optional<Attendant> findAvailableAttendant(Department department) {
    return attendantRepository.findByDepartmentAndNumberOfTicketsLessThan(
        department,
        3
    );
  }
}
