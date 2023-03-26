package com.codegym.cms.service;

import com.codegym.cms.repository.ProvinceRepository;
import com.codegym.cms.service.impl.ProvinceServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProvinceServiceImplTestConfig {

    @Bean
    public ProvinceService provinceService(){
        return new ProvinceServiceImpl(provinceRepository());
    }

    @Bean
    public ProvinceRepository provinceRepository(){
        return Mockito.mock(ProvinceRepository.class);
    }
}
