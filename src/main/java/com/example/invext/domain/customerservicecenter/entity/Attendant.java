package com.example.invext.domain.customerservicecenter.entity;

import com.example.invext.domain.customerservicecenter.enumeration.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Attendant {
  private Integer id;
  private String name;
  private String email;
  private Department department;
  private List<Ticket> ticketList;
}
