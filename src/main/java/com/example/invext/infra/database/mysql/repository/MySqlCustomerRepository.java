package com.example.invext.infra.database.mysql.repository;

import com.example.invext.domain.customerservicecenter.entity.Customer;
import com.example.invext.domain.customerservicecenter.repository.ICustomerRepository;
import com.example.invext.infra.database.mysql.datamapper.CustomerDataMapper;
import com.example.invext.infra.database.mysql.entity.CustomerJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class MySqlCustomerRepository implements ICustomerRepository {
  @Autowired
  private final CustomerJpaRepository customerJpaRepository;
  @Autowired
  private final CustomerDataMapper customerDataMapper;


  @Override
  public Optional<Customer> findById(Integer id) {
    Optional<CustomerJpaEntity> entity = customerJpaRepository.findById(id);

    if (entity.isEmpty()) return Optional.empty();
    else return Optional.of(this.customerDataMapper.toDomain(entity.get()));
  }
}
