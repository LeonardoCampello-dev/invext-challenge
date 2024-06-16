package com.example.invext.domain.customerservicecenter.repository;

import com.example.invext.domain.customerservicecenter.entity.Ticket;

public interface ITicketRepository {
  Ticket save(Ticket ticket);
}

