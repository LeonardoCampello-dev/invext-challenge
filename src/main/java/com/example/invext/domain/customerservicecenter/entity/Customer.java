package com.example.invext.domain.customerservicecenter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class Customer {
  private Integer id;
  private String name;
  private String email;
  private List<Ticket> ticketList;
}

