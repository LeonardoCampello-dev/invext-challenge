package com.example.invext.domain.customerservicecenter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Customer {
  private String id;
  private String name;
  private String email;
  private List<Ticket> ticketList;
}

