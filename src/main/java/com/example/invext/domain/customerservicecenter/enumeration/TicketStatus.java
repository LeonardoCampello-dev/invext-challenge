package com.example.invext.domain.customerservicecenter.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TicketStatus {
  OPEN("Aberto"),
  IN_PROGRESS("Em andamento"),
  CLOSED("Fechado");

  @Getter
  private final String value;
}
