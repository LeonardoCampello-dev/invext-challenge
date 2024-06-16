package com.example.invext.infra.database.mysql.datamapper;

import com.example.invext.domain.customerservicecenter.entity.Customer;
import com.example.invext.infra.contract.IDataMapper;
import com.example.invext.infra.database.mysql.entity.CustomerJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CustomerDataMapper implements IDataMapper<Customer, CustomerJpaEntity> {
  @Autowired
  private final TicketDataMapper ticketDataMapper;

  public Customer toDomain(CustomerJpaEntity customerJpaEntity) {
    return new Customer(
        customerJpaEntity.getId(),
        customerJpaEntity.getName(),
        customerJpaEntity.getEmail(),
        customerJpaEntity
            .getTicketList()
            .stream()
            .map(ticketDataMapper::toDomain)
            .collect(Collectors.toList())
    );
  }

  public CustomerJpaEntity toEntity(Customer customer) {
    return new CustomerJpaEntity(
        customer.getId(),
        customer.getName(),
        customer.getEmail(),
        customer
            .getTicketList()
            .stream()
            .map(ticketDataMapper::toEntity)
            .collect(Collectors.toList())
    );
  }
}
