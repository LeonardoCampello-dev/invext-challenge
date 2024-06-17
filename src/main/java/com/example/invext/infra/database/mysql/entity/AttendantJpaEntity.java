package com.example.invext.infra.database.mysql.entity;

import com.example.invext.domain.customerservicecenter.enumeration.Department;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity()
@Table(name = "attendant")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AttendantJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Department department;

  @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
  private List<TicketJpaEntity> ticketList;
}
