package com.example.invext.infra.database.mysql.datamapper;

import com.example.invext.domain.customerservicecenter.entity.Ticket;
import com.example.invext.infra.contract.IDataMapper;
import com.example.invext.infra.database.mysql.entity.TicketJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TicketDataMapper implements IDataMapper<Ticket, TicketJpaEntity> {
  @Autowired
  private final AttendantDataMapper attendantDataMapper;
  @Autowired
  private final CustomerDataMapper customerDataMapper;

  @Override
  public Ticket toDomain(TicketJpaEntity ticketJpaEntity) {
    return new Ticket(
        ticketJpaEntity.getId(),
        ticketJpaEntity.getDepartment(),
        ticketJpaEntity.getStatus(),
        ticketJpaEntity.getTitle(),
        ticketJpaEntity.getDescription(),
        ticketJpaEntity.getCreatedAt(),
        ticketJpaEntity.getUpdatedAt(),
        ticketJpaEntity.getClosedAt(),
        attendantDataMapper.toDomain(ticketJpaEntity.getAssignee()),
        customerDataMapper.toDomain(ticketJpaEntity.getCustomer())

    );
  }

  @Override
  public TicketJpaEntity toEntity(Ticket ticket) {
    return new TicketJpaEntity(
        ticket.getId(),
        ticket.getDepartment(),
        ticket.getStatus(),
        ticket.getTitle(),
        ticket.getDescription(),
        ticket.getCreatedAt(),
        ticket.getUpdatedAt(),
        ticket.getClosedAt(),
        attendantDataMapper.toEntity(ticket.getAssignee()),
        customerDataMapper.toEntity(ticket.getCustomer())
    );
  }
}
