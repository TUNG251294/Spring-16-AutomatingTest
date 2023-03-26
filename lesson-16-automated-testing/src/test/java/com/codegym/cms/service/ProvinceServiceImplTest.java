package com.codegym.cms.service;

import com.codegym.cms.model.Province;
import com.codegym.cms.repository.ProvinceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringJUnitJupiterConfig(ProvinceServiceImplTestConfig.class)
class ProvinceServiceImplTest {

    static Long id;
    static Province province;
    static ArrayList<Province> provinces;
    static ArrayList<Province> emptyProvinces;

    static {
        id = 1l;
        province = new Province("Sai Gon");
        provinces = new ArrayList<>();
        provinces.add(province);

        emptyProvinces = new ArrayList<>();
    }

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private ProvinceRepository provinceRepository;

    @AfterEach
    public void resetMocks(){
        reset(provinceRepository);
    }

    @Test
    void findAll_1Customer() {
        when(provinceRepository.findAll()).thenReturn(provinces);
        Iterable<Province> result = provinceService.findAll();

        verify(provinceRepository).findAll();
        assertEquals(provinces, result);
    }

    @Test
    void findAll_0Customer() {
        when(provinceRepository.findAll()).thenReturn(emptyProvinces);
        Iterable<Province> result = provinceService.findAll();

        verify(provinceRepository).findAll();
        assertEquals(emptyProvinces, result);
    }

    @Test
    void findById_Found() {
        when(provinceRepository.findById(id)).thenReturn(province);
        Province result = provinceService.findById(id);

        verify(provinceRepository).findById(id);
        assertEquals(province, result);
    }

    @Test
    void findById_NotFound() {
        when(provinceRepository.findById(id)).thenReturn(null);
        Province result = provinceService.findById(id);

        verify(provinceRepository).findById(id);
        assertNull(result);
    }

    @Test
    void save() {
        provinceService.save(province);
        verify(provinceRepository).save(province);
    }

    @Test
    void remove() {
        provinceService.remove(id);
        verify(provinceRepository).delete(id);
    }
}