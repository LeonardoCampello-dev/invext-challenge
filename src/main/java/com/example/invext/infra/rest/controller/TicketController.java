package com.example.invext.infra.rest.controller;

import com.example.invext.application.usecase.ticket.OpenTicketUseCase;
import com.example.invext.infra.rest.dto.OpenTicketDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
  @ApiResponses({
      @ApiResponse(code = 200, message = "Ticket criado com sucesso!"),
      @ApiResponse(code = 404, message = "Cliente não encontrado")
  })
  @ApiOperation(value = "Efetua a abertura de um ticket")
  public void openTicket(
      @RequestBody OpenTicketDTO dto
  ) {
    openTicketUseCase.execute(dto);
  }
}
