package com.codegym.cms.repository;

import com.codegym.cms.repository.impl.ProvinceRepositoryImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Configuration
public class ProvinceRepositoryImplTestConfig {

    @Bean
    public ProvinceRepository provinceRepository(){
        return new ProvinceRepositoryImpl();
    }

    @Bean
    public EntityManager entityManager() {
        return Mockito.mock(EntityManager.class);//giả lập lớp
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(){
//        EntityManagerFactory entityManagerFactory = Mockito.mock(EntityManagerFactory.class);
//        return entityManagerFactory;
        return Mockito.mock(EntityManagerFactory.class);
    }
}
