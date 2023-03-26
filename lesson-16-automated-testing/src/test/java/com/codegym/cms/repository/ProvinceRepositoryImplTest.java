package com.codegym.cms.repository;

import com.codegym.cms.model.Customer;
import com.codegym.cms.model.Province;
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

@ExtendWith(SpringExtension.class)
@SpringJUnitJupiterConfig(ProvinceRepositoryImplTestConfig.class)//thu vien JUnitJupiter
class ProvinceRepositoryImplTest {

    private static Province province;
    private static List<Province> emptyProvinces;
    private static List<Province> provinces;
//tao 2 ds rong va ds co Sai Gon
    static {
        province = new Province("Sai Gon");
        emptyProvinces = new ArrayList<>();
        provinces = new ArrayList<>();
        provinces.add(province);
    }


    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setupEntityManager(){
        when(entityManagerFactory.createEntityManager()).thenReturn(em);
    }
//goi ra entity va tra ve gia lap da mock(em)
    @AfterEach
    void resetMocks(){
        reset(entityManagerFactory);
        reset(em);
    }

    @Test
    void findAllWith1Province() {
        TypedQuery<Province> query = mock(TypedQuery.class);//chuan bi doi tuong query
        String queryString = "select p from Province p";    //chuan bi cau query

        when(em.createQuery(queryString, Province.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(provinces);
//follow theo ProvinceRepositoryImpl de viet
        List<Province> result = provinceRepository.findAll();
        verify(em).createQuery(queryString, Province.class);
        verify(query).getResultList();
        assertEquals(provinces, result);
    }

    @Test
    void findAllWith0Province() {
        TypedQuery<Province> query = mock(TypedQuery.class);
        String queryString = "select p from Province p";

        when(em.createQuery(queryString, Province.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(emptyProvinces);

        List<Province> result = provinceRepository.findAll();
        verify(em).createQuery(queryString, Province.class);
        verify(query).getResultList();
        assertEquals(emptyProvinces, result);   //so sanh ket qua mong doi va ket qua tra ve
    }

    @Test
    void findByIdFound() {
        Long id = 1l;
        TypedQuery<Province> query = mock(TypedQuery.class);
        String queryString = "select p from Province p where p.id=:id";

        when(em.createQuery(queryString, Province.class)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(province);

        Province result = provinceRepository.findById(id);
        verify(em).createQuery(queryString, Province.class);
        verify(query).getSingleResult();
        verify(query).setParameter("id", id);
        assertEquals(province, result);
    }

    @Test
    void findByIdNull() {
        Long id = 1l;
        TypedQuery<Province> query = mock(TypedQuery.class);
        String queryString = "select p from Province p where p.id=:id";

        when(em.createQuery(queryString, Province.class)).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new NoResultException());

        Province result = provinceRepository.findById(id);
        verify(em).createQuery(queryString, Province.class);
        verify(query).getSingleResult();
        verify(query).setParameter("id", id);
        assertNull(result);
    }
}
