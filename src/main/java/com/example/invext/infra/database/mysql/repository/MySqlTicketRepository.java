package com.example.invext.infra.database.mysql.repository;

import com.example.invext.domain.customerservicecenter.entity.Ticket;
import com.example.invext.domain.customerservicecenter.repository.ITicketRepository;
import com.example.invext.infra.database.mysql.datamapper.TicketDataMapper;
import com.example.invext.infra.database.mysql.entity.TicketJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MySqlTicketRepository implements ITicketRepository {
  @Autowired
  private final TicketJpaRepository ticketJpaRepository;
  @Autowired
  private final TicketDataMapper ticketDataMapper;

  @Override
  public Ticket save(Ticket ticket) {
    TicketJpaEntity saved = ticketJpaRepository.save(ticketDataMapper.toEntity(ticket));

    return this.ticketDataMapper.toDomain(saved);
  }
}
