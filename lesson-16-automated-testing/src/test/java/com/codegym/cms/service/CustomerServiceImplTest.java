package com.codegym.cms.service;

import com.codegym.cms.model.Customer;
import com.codegym.cms.model.Province;
import com.codegym.cms.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringJUnitJupiterConfig(CustomerServiceImplTestConfig.class)
class CustomerServiceImplTest {

    private static Long id;
    private static String name = "Name";
    private static String email = "Email";
    private static String address = "Address";
    private static ArrayList<Customer> customers;
    private static ArrayList<Customer> emptyCustomers;
    private static Page<Customer> customersPage;
    private static Page<Customer> emptyCustomersPage;
    private static Customer customer;
    private static Pageable pageable;
    private static Province province;

    static {
        id = 1l;
        customer = new Customer(name, email, address);

        emptyCustomers = new ArrayList<>();
        customers = new ArrayList<>();

        customers.add(customer);

        emptyCustomersPage = new PageImpl<>(customers);
        customersPage = new PageImpl<>(customers);

//        pageable = new PageRequest(0, 20);

        province = new Province("Hanoi");
    }

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    public void resetMocks(){
        reset(customerRepository);
    }

    @Test
    void findAll_1customer() {
        when(customerRepository.findAll(pageable)).thenReturn(customersPage);
        Page<Customer> result = customerService.findAll(pageable);

        verify(customerRepository).findAll(pageable);
        assertEquals(customersPage, result);
    }

    @Test
    void findAll_0customer() {
        when(customerRepository.findAll(pageable)).thenReturn(emptyCustomersPage);
        Page<Customer> result = customerService.findAll(pageable);

        verify(customerRepository).findAll(pageable);
        assertEquals(emptyCustomersPage, result);
    }

    @Test
    void findById_Found() {
        when(customerRepository.findById(id)).thenReturn(customer);
        Customer result = customerService.findById(id);

        verify(customerRepository).findById(id);
        assertEquals(customer, result);
    }

    @Test
    void findById_NotFound() {
        when(customerRepository.findById(id)).thenReturn(null);
        Customer result = customerService.findById(id);

        verify(customerRepository).findById(id);
        assertNull(result);
    }

    @Test
    void save(){
        customerService.save(customer);
        verify(customerRepository).save(customer);
    }

    @Test
    void remove(){
        customerService.delete(id);
        verify(customerRepository).delete(id);
    }

    @Test
    void findAllByProvince() {
        when(customerRepository.findAllByProvince(province)).thenReturn(customers);
        Iterable<Customer> result = customerService.findAllByProvince(province);

        verify(customerRepository).findAllByProvince(province);
        assertEquals(customers, result);
    }

    @Test
    void findAllByFirstNameContaining_1Customer() {
        when(customerRepository.findAllByNameContaining(name, pageable)).thenReturn(customersPage);
        Page<Customer> result = customerService.findAllByNameContaining(name, pageable);

        verify(customerRepository).findAllByNameContaining(name,pageable);
        assertEquals(customersPage, result);
    }

    @Test
    void findAllByFirstNameContaining_0Customer() {
        when(customerRepository.findAllByNameContaining(name, pageable)).thenReturn(emptyCustomersPage);
        Page<Customer> result = customerService.findAllByNameContaining(name, pageable);

        verify(customerRepository).findAllByNameContaining(name,pageable);
        assertEquals(emptyCustomersPage, result);
    }
}