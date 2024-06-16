package com.example.invext.domain.customerservicecenter.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor()
public enum Department {
  CREDIT_CARDS("Cartões"),
  LOANS("Empréstimos"),
  OTHERS("Outros");

  @Getter
  private final String value;
}
