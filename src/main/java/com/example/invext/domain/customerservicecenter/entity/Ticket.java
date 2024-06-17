package com.example.invext.domain.customerservicecenter.entity;

import com.example.invext.domain.customerservicecenter.enumeration.Department;
import com.example.invext.domain.customerservicecenter.enumeration.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@AllArgsConstructor
@ToString
public class Ticket implements Serializable {
  private Integer id;

  private Department department;
  private TicketStatus status;

  private String title;
  private String description;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime closedAt;

  private Attendant assignee;
  private Customer customer;

  public Ticket(
      Department department,
      String title,
      String description,
      Customer customer
  ) {
    this.department = department;
    this.title = title;
    this.description = description;
    this.customer = customer;

    this.status = TicketStatus.OPEN;
    this.createdAt = LocalDateTime.now();
  }

  public void linkAttendant(Attendant attendant) {
    this.assignee = attendant;
    this.status = TicketStatus.IN_PROGRESS;
  }

  public Long getWaitingTimeInSeconds() {
    return ChronoUnit.SECONDS.between(
        createdAt,
        LocalDateTime.now()
    );
  }
}
