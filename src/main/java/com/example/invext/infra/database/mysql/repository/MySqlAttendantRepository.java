package com.example.invext.infra.database.mysql.repository;

import com.example.invext.domain.customerservicecenter.entity.Attendant;
import com.example.invext.domain.customerservicecenter.enumeration.Department;
import com.example.invext.domain.customerservicecenter.repository.IAttendantRepository;
import com.example.invext.infra.database.mysql.datamapper.AttendantDataMapper;
import com.example.invext.infra.database.mysql.entity.AttendantJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class MySqlAttendantRepository implements IAttendantRepository {
  @Autowired
  private final AttendantJpaRepository attendantJpaRepository;
  @Autowired
  private final AttendantDataMapper attendantDataMapper;


  @Override
  public Optional<Attendant> findByDepartmentAndNumberOfTicketsLessThan(
      Department department,
      Integer lessThan
  ) {
    Optional<AttendantJpaEntity> entity = attendantJpaRepository.findByDepartmentAndNumberOfTicketsLessThan(
        department,
        lessThan
    );

    if (entity.isEmpty()) return Optional.empty();
    else return Optional.of(attendantDataMapper.toDomain(entity.get()));
  }
}
