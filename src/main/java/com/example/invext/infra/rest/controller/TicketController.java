package com.example.invext.infra.rest.controller;

import com.example.invext.application.usecase.ticket.OpenTicketUseCase;
import com.example.invext.domain.customerservicecenter.entity.Ticket;
import com.example.invext.infra.rest.dto.OpenTicketDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("ticket")
public class TicketController {
  @Autowired
  private final OpenTicketUseCase openTicketUseCase;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Transactional
  public ResponseEntity<Ticket> openTicket(
      @RequestBody OpenTicketDTO dto
  ) {
    Ticket result = openTicketUseCase.execute(dto);

    if (result == null) {
      return ResponseEntity
          .notFound()
          .build();
    } else {
      // TODO Correto seria retornar um DTO
      return ResponseEntity.ok(result);
    }
  }
}
