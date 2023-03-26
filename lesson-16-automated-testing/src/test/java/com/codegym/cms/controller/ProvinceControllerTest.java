package com.codegym.cms.controller;

import com.codegym.cms.config.H2TestProfileConfig;
import com.codegym.cms.configuration.ApplicationConfig;
import com.codegym.cms.model.Customer;
import com.codegym.cms.model.Province;
import com.codegym.cms.service.CustomerService;
import com.codegym.cms.service.ProvinceService;
import com.codegym.cms.service.impl.CustomerServiceImpl;
import com.codegym.cms.service.impl.ProvinceServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitJupiterConfig(ProvinceControllerTestConfig.class)
@ContextConfiguration(classes = {
        ApplicationConfig.class,
        H2TestProfileConfig.class})
@WebAppConfiguration
@ActiveProfiles("test")
@ComponentScan("com.codegym.cms.controller")
class ProvinceControllerTest {
    private static final String VIEW_ERROR_404 = "/error.404";
    private static final String VIEW_CREATE_PROVINCE = "/province/create";
    private static final String VIEW_PROVINCE_LIST = "/province/list";
    private static final String VIEW_EDIT_PROVINCE = "/province/edit";
    private static final String VIEW_DELETE_PROVINCE = "/province/delete";
    private static final String VIEW_VIEW_PROVINCE = "/province/view";

    private static final String URL_CREATE_PROVINCE = "/create-province";
    private static final String URL_PROVINCE_LIST = "/provinces";
    private static final String URL_EDIT_PROVINCE = "/edit-province/{id}";
    private static final String URL_EDIT_PROVINCE_POST = "/edit-province";
    private static final String URL_DELETE_PROVINCE = "/delete-province/{id}";
    private static final String URL_DELETE_PROVINCE_POST = "/delete-province";
    private static final String URL_VIEW_PROVINCE = "/view-province/{id}";
    static Long id;
    static Long notFoundId;
    static Province province;
    static ArrayList<Province> provinces;
    static ArrayList<Province> emptyProvinces;
    static String provinceName = "Sai Gon";
    private static Customer customer;
    private static ArrayList<Customer> customers;

    static {
        notFoundId = 0L;
        id = 1L;

        province = new Province(id, provinceName);

        provinces = new ArrayList<>();
        provinces.add(province);

        emptyProvinces = new ArrayList<>();

        customers = new ArrayList<>();
        customer = new Customer("A", "B", "C");
        customers.add(customer);
    }

    private MockMvc mockMvc;

    @Autowired
    private ProvinceController provinceController;

    private ProvinceService provinceService =
            Mockito.mock(ProvinceServiceImpl.class);

    private CustomerService customerService =
            Mockito.mock(CustomerServiceImpl.class);

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(provinceController)
                .build();
    }

    @AfterEach
    void resetMocks(){
        reset(provinceService);
    }

    @Test
    void listProvinces_Empty() throws Exception {
        when(provinceService.findAll()).thenReturn(emptyProvinces);

        mockMvc.perform(get(URL_PROVINCE_LIST))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_PROVINCE_LIST))
                .andExpect(model().attributeExists("provinces"))
                .andExpect(model().attribute("provinces", hasSize(0)));
        verify(provinceService, never()).findAll();
    }

    @Test
    void showCreateForm() throws Exception {
        mockMvc.perform(get(URL_CREATE_PROVINCE))
                .andExpect(view().name(VIEW_CREATE_PROVINCE))
                .andExpect(model().attributeExists("province"))
                .andExpect(model().attribute("province", instanceOf(Province.class)));
        verify(provinceService, never()).findAll();
    }

    @Test
    void saveProvince_Success() throws Exception {
        mockMvc.perform(post(URL_CREATE_PROVINCE)
                .param("name", provinceName))
                .andExpect(view().name(VIEW_CREATE_PROVINCE))
                .andExpect(model().attributeExists("province"))
                .andExpect(model().attributeExists("message"));
        verify(provinceService, never()).save(any(Province.class));
    }

    @Test
    void listProvinces() throws Exception {
        when(provinceService.findAll()).thenReturn(provinces);

        mockMvc.perform(get(URL_PROVINCE_LIST))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_PROVINCE_LIST))
                .andExpect(model().attributeExists("provinces"))
                .andExpect(model().attribute("provinces", hasSize(1)));
        verify(provinceService, never()).findAll();
    }

    @Test
    void showEditForm_Found() throws Exception {
        when(provinceService.findById(id)).thenReturn(province);

        //id should be found in h2. If not, test case is failed
        mockMvc.perform(get(URL_EDIT_PROVINCE, id))
                .andExpect(view().name(VIEW_EDIT_PROVINCE))
                .andExpect(model().attributeExists("province"))
                .andExpect(model().attribute("province", instanceOf(Province.class)));
        verify(provinceService, never()).findById(id);
    }

    @Test
    void showEditForm_NotFound() throws Exception {
        when(provinceService.findById(notFoundId)).thenReturn(null);

        mockMvc.perform(get(URL_EDIT_PROVINCE, notFoundId))
                .andExpect(view().name(VIEW_ERROR_404));
        verify(provinceService, never()).findById(notFoundId);
    }

    @Test
    void updateProvince_Success() throws Exception {
        //id should be found in h2. If not, test case is failed
        mockMvc.perform(post(URL_EDIT_PROVINCE_POST, id)
                .param("name", "Sai Gon2"))
                .andExpect(view().name(VIEW_EDIT_PROVINCE))
                .andExpect(model().attributeExists("province"))
                .andExpect(model().attributeExists("message"));
        verify(provinceService, never()).save(any(Province.class));
    }

    @Test
    void showDeleteForm_Found() throws Exception {
        when(provinceService.findById(id)).thenReturn(province);

        //id should be found in h2. If not, test case is failed
        mockMvc.perform(get(URL_DELETE_PROVINCE, id))
                .andExpect(view().name(VIEW_DELETE_PROVINCE))
                .andExpect(model().attributeExists("province"))
                .andExpect(model().attribute("province", instanceOf(Province.class)));
        verify(provinceService, never()).findById(id);
    }

    @Test
    void showDeleteForm_NotFound() throws Exception {
        when(provinceService.findById(notFoundId)).thenReturn(null);

        mockMvc.perform(get(URL_DELETE_PROVINCE, notFoundId))
                .andExpect(view().name(VIEW_ERROR_404));
        verify(provinceService, never()).findById(notFoundId);
    }

    @Test
    void deleteProvince_Success() throws Exception {
        //id should be found in h2. If not, test case is failed
        mockMvc.perform(post(URL_DELETE_PROVINCE_POST)
                .param("id", id + ""))
                .andExpect(view().name("redirect:provinces"));
        verify(provinceService, never()).remove(id);
    }

    @Test
    void viewProvince() throws Exception {
        when(provinceService.findById(id)).thenReturn(province);
        when(customerService.findAllByProvince(province)).thenReturn(customers);

        //id should be found in h2. If not, test case is failed
        mockMvc.perform(get(URL_VIEW_PROVINCE, id))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_VIEW_PROVINCE))
                .andExpect(model().attributeExists("province"))
                .andExpect(model().attributeExists("customers"))
                .andExpect(model().attribute("province", instanceOf(Province.class)));
        verify(provinceService, never()).findById(id);
        verify(customerService, never()).findAllByProvince(province);
    }

    @Test
    void viewProvince_NotFound() throws Exception {
        when(provinceService.findById(notFoundId)).thenReturn(null);

        mockMvc.perform(get(URL_VIEW_PROVINCE, notFoundId))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_ERROR_404));
        verify(provinceService, never()).findById(notFoundId);
    }
}