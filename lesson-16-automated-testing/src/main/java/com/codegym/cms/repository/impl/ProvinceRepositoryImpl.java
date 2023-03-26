package com.codegym.cms.repository.impl;

import com.codegym.cms.model.Province;
import com.codegym.cms.repository.ProvinceRepository;
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
public class ProvinceRepositoryImpl implements ProvinceRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Province> findAll(Pageable pageable) {
        TypedQuery<Province> query = em.createQuery("select p from Province p", Province.class);
        return new PageImpl<>(query.getResultList());
    }

    @Override
    public List<Province> findAll() {
        TypedQuery<Province> query = em.createQuery("select p from Province p", Province.class);
        return query.getResultList();
    }

    @Override
    public Province findById(Long id) {
        TypedQuery<Province> query = em.createQuery("select p from Province p where p.id=:id", Province.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public void save(Province model) {
        if(model.getId() != null){
            em.merge(model);
        } else {
            em.persist(model);
        }
    }

    @Override
    public void delete(Long id) {
        Province province = findById(id);
        if(province != null){
            em.remove(province);
        }
    }

    @Override
    public Province findByName(String name) {
        TypedQuery<Province> query = em.createQuery("select p from Province p where p.name=:name", Province.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
}
