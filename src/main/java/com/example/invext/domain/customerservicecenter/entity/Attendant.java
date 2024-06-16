package com.example.invext.domain.customerservicecenter.entity;

import lombok.Getter;

import java.util.List;

@Getter
public class Attendant {
  private Integer id;
  private String name;
  private String email;
  private List<Ticket> ticketList;
}
