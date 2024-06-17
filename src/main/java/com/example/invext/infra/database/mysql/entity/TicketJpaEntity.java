package com.example.invext.infra.database.mysql.entity;

import com.example.invext.domain.customerservicecenter.enumeration.Department;
import com.example.invext.domain.customerservicecenter.enumeration.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TicketJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Department department;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private TicketStatus status;

  @Column(nullable = false)
  private String title;
  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;

  private LocalDateTime closedAt;

  @ManyToOne(optional = true)
  @JoinColumn(name = "assignee_id")
  private AttendantJpaEntity assignee;

  @ManyToOne()
  @JoinColumn(name = "customer_id", nullable = false)
  private CustomerJpaEntity customer;
}
