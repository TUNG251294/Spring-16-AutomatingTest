package com.codegym.cms.repository;

import com.codegym.cms.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringJUnitJupiterConfig(CustomerRepositoryImplTestConfig.class)
class CustomerRepositoryImplTest {

    private static Customer customer;
    private static List<Customer> emptyCustomers;
    private static List<Customer> customers;

    static {
        customer = new Customer("An Nguyen", "an@gmail.com", "21K NVT");
        emptyCustomers = new ArrayList<>();
        customers = new ArrayList<>();
        customers.add(customer);
    }

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setupEntityManager(){
        when(entityManagerFactory.createEntityManager()).thenReturn(em);
    }

    @AfterEach
    void resetMocks(){
        reset(entityManagerFactory);
        reset(em);
    }

    @Test
    void findAllWith1Customer() {
        TypedQuery<Customer> query = mock(TypedQuery.class);
        String queryString = "select c from Customer c";

        when(em.createQuery(queryString, Customer.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(customers);

        List<Customer> result = customerRepository.findAll();
        verify(em).createQuery(queryString, Customer.class);
        verify(query).getResultList();
        assertEquals(customers, result);
    }

    @Test
    void findAllWith0Customer() {
        TypedQuery<Customer> query = mock(TypedQuery.class);
        String queryString = "select c from Customer c";

        when(em.createQuery(queryString, Customer.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(emptyCustomers);

        List<Customer> result = customerRepository.findAll();
        verify(em).createQuery(queryString, Customer.class);
        verify(query).getResultList();
        assertEquals(emptyCustomers, result);
    }

    @Test
    void findByIdFound() {
        Long id = 1l;
        TypedQuery<Customer> query = mock(TypedQuery.class);
        String queryString = "select c from Customer c where  c.id=:id";

        when(em.createQuery(queryString, Customer.class)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(customer);

        Customer result = customerRepository.findById(id);
        verify(em).createQuery(queryString, Customer.class);
        verify(query).getSingleResult();
        verify(query).setParameter("id", id);
        assertEquals(customer, result);
    }

    @Test
    void findByIdNull() {
        Long id = 1l;
        TypedQuery<Customer> query = mock(TypedQuery.class);
        String queryString = "select c from Customer c where  c.id=:id";

        when(em.createQuery(queryString, Customer.class)).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new NoResultException());

        Customer result = customerRepository.findById(id);
        verify(em).createQuery(queryString, Customer.class);
        verify(query).getSingleResult();
        verify(query).setParameter("id", id);
        assertNull(result);
    }
}
