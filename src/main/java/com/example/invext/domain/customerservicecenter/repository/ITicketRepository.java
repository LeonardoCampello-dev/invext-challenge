package com.example.invext.domain.customerservicecenter.repository;

import com.example.invext.domain.customerservicecenter.entity.Ticket;

import java.util.Optional;

public interface ITicketRepository {
  Ticket save(Ticket ticket);
  Optional<Ticket> findById(Integer id);
}

