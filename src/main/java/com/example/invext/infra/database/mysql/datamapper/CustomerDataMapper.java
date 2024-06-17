package com.example.invext.infra.database.mysql.datamapper;

import com.example.invext.domain.customerservicecenter.entity.Customer;
import com.example.invext.infra.contract.IDataMapper;
import com.example.invext.infra.database.mysql.entity.CustomerJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class CustomerDataMapper implements IDataMapper<Customer, CustomerJpaEntity> {
  public Customer toDomain(CustomerJpaEntity customerJpaEntity) {
    return new Customer(
        customerJpaEntity.getId(),
        customerJpaEntity.getName(),
        customerJpaEntity.getEmail(),
        new ArrayList<>()
    );
  }

  public CustomerJpaEntity toEntity(Customer customer) {
    return new CustomerJpaEntity(
        customer.getId(),
        customer.getName(),
        customer.getEmail(),
        new ArrayList<>()
    );
  }
}
