package com.example.invext.infra.database.mysql.datamapper;

import com.example.invext.domain.customerservicecenter.entity.Attendant;
import com.example.invext.infra.contract.IDataMapper;
import com.example.invext.infra.database.mysql.entity.AttendantJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AttendantDataMapper implements IDataMapper<Attendant, AttendantJpaEntity> {
  @Autowired
  private final TicketDataMapper ticketDataMapper;

  @Override
  public Attendant toDomain(AttendantJpaEntity attendantJpaEntity) {
    return new Attendant(
        attendantJpaEntity.getId(),
        attendantJpaEntity.getName(),
        attendantJpaEntity.getEmail(),
        attendantJpaEntity.getDepartment(),
        attendantJpaEntity
            .getTicketList()
            .stream()
            .map(ticketDataMapper::toDomain)
            .collect(Collectors.toList())
    );
  }

  @Override
  public AttendantJpaEntity toEntity(Attendant attendant) {
    return new AttendantJpaEntity(
        attendant.getId(),
        attendant.getName(),
        attendant.getEmail(),
        attendant.getDepartment(),
        attendant
            .getTicketList()
            .stream()
            .map(ticketDataMapper::toEntity)
            .collect(Collectors.toList())
    );
  }
}
