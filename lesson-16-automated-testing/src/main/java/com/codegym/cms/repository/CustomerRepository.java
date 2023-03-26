package com.codegym.cms.repository;

import com.codegym.cms.model.Customer;
import com.codegym.cms.model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerRepository extends GeneralRepository<Customer> {
    Iterable<Customer> findAllByProvince(Province province);

    Page<Customer> findAllByNameContaining(String name, Pageable pageable);
}
