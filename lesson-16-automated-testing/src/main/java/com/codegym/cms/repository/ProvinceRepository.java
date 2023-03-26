package com.codegym.cms.repository;

import com.codegym.cms.model.Province;

public interface ProvinceRepository extends GeneralRepository<Province> {

    Province findByName(String name);
}
