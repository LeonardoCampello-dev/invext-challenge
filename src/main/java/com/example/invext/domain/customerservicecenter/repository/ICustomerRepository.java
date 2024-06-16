package com.example.invext.domain.customerservicecenter.repository;

import com.example.invext.domain.customerservicecenter.entity.Customer;

import java.util.Optional;

public interface ICustomerRepository {
  Optional<Customer> findById(String id);
}
