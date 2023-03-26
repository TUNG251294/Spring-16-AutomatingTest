package com.codegym.cms.repository.impl;

import com.codegym.cms.model.Customer;
import com.codegym.cms.model.Province;
import com.codegym.cms.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CustomerRepositoryImpl implements CustomerRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Iterable<Customer> findAllByProvince(Province province) {
        TypedQuery<Customer> query =
                em.createQuery("select c from Customer c where c.province.id=:id", Customer.class);
        query.setParameter("id", province.getId());
        return new PageImpl<>(query.getResultList());
    }

    @Override
    public Page<Customer> findAllByNameContaining(String name, Pageable pageable) {
        TypedQuery<Customer> query =
                em.createQuery("select c from Customer c where c.name=:name", Customer.class);
        query.setParameter("name", name);
        return new PageImpl<>(query.getResultList());
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        TypedQuery<Customer> query = em.createQuery("select c from Customer c", Customer.class);
        return new PageImpl<>(query.getResultList());
    }

    @Override
    public List<Customer> findAll() {
        TypedQuery<Customer> query = em.createQuery("select c from Customer c", Customer.class);
        return query.getResultList();
    }

    @Override
    public Customer findById(Long id) {
        TypedQuery<Customer> query = em.createQuery("select c from Customer c where  c.id=:id", Customer.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public void save(Customer model) {
        if(model.getId() != null){
            em.merge(model);
        } else {
            em.persist(model);
        }
    }

    @Override
    public void delete(Long id) {
        Customer customer = findById(id);
        if(customer != null){
            em.remove(customer);
        }
    }
}
