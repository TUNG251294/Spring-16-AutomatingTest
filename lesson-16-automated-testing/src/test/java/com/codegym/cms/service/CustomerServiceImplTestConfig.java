package com.codegym.cms.service;

import com.codegym.cms.repository.CustomerRepository;
import com.codegym.cms.service.impl.CustomerServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerServiceImplTestConfig {

    @Bean
    public CustomerService customerService(){
        return new CustomerServiceImpl(customerRepository());
    }

    @Bean
    public CustomerRepository customerRepository(){
        return Mockito.mock(CustomerRepository.class);
    }
}
