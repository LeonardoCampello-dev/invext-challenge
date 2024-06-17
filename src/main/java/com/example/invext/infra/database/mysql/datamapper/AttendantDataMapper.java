package com.example.invext.infra.database.mysql.datamapper;

import com.example.invext.domain.customerservicecenter.entity.Attendant;
import com.example.invext.infra.contract.IDataMapper;
import com.example.invext.infra.database.mysql.entity.AttendantJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class AttendantDataMapper implements IDataMapper<Attendant, AttendantJpaEntity> {
  @Override
  public Attendant toDomain(AttendantJpaEntity attendantJpaEntity) {
    return new Attendant(
        attendantJpaEntity.getId(),
        attendantJpaEntity.getName(),
        attendantJpaEntity.getEmail(),
        attendantJpaEntity.getDepartment(),
        new ArrayList<>()
    );
  }

  @Override
  public AttendantJpaEntity toEntity(Attendant attendant) {
    return new AttendantJpaEntity(
        attendant.getId(),
        attendant.getName(),
        attendant.getEmail(),
        attendant.getDepartment(),
        new ArrayList<>()
    );
  }
}
