package com.codegym.cms.repository;

import com.codegym.cms.repository.CustomerRepository;
import com.codegym.cms.repository.impl.CustomerRepositoryImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Configuration
public class CustomerRepositoryImplTestConfig {

    @Bean
    public CustomerRepository customerRepository(){
        return new CustomerRepositoryImpl();
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return Mockito.mock(EntityManager.class);
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(){
        EntityManagerFactory entityManagerFactory = Mockito.mock(EntityManagerFactory.class);
        return entityManagerFactory;
    }
}
