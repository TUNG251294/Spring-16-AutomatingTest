package com.codegym.cms.service.impl;

import com.codegym.cms.model.Province;
import com.codegym.cms.repository.ProvinceRepository;
import com.codegym.cms.service.ProvinceService;

public class ProvinceServiceImpl implements ProvinceService {

    private ProvinceRepository provinceRepository;

    public ProvinceServiceImpl(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    @Override
    public Iterable<Province> findAll() {
        return provinceRepository.findAll();
    }

    @Override
    public Province findById(Long id) {
        return provinceRepository.findById(id);
    }

    @Override
    public void save(Province province) {
        provinceRepository.save(province);
    }

    @Override
    public void remove(Long id) {
        provinceRepository.delete(id);
    }
}
