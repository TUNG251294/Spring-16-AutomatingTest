package com.codegym.cms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GeneralRepository<T> {
    Page<T> findAll(Pageable pageable);

    List<T> findAll();

    T findById(Long id);

    void save(T model);

    void delete(Long id);
}
