package com.codegym.cms.formatter;

import com.codegym.cms.model.Province;
import com.codegym.cms.service.ProvinceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

import java.text.ParseException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitJupiterConfig(ProvinceFormatterTestConfig.class)
class ProvinceFormatterTest {

    private static Long id;
    private static Province province;

    static {
        id = 1l;
        province = new Province("Sai Gon");
        province.setId(id);
    }

    @Autowired
    private ProvinceService provinceService;

    @Test
    void parse_success() {
        when(provinceService.findById(id)).thenReturn(province);
        ProvinceFormatter provinceFormatter = new ProvinceFormatter(provinceService);
        try {
            Province result = provinceFormatter.parse(id + "", Locale.getDefault());
            verify(provinceService).findById(id);
            assertEquals(province, result);
        } catch (ParseException e) {
            assertFalse(true);
        }
    }

    @Test
    void parse_exception() {
        ProvinceFormatter provinceFormatter = new ProvinceFormatter(provinceService);
        assertThrows(NumberFormatException.class, () -> {
            Province result = provinceFormatter.parse("a", Locale.getDefault());
        });
    }

    @Test
    void print() {
        when(provinceService.findById(id)).thenReturn(province);
        ProvinceFormatter provinceFormatter = new ProvinceFormatter(provinceService);
        String result = provinceFormatter.print(province, Locale.getDefault());
        String expected = "[" + province.getId() + ", " +province.getName() + "]";

        verify(provinceService).findById(id);
        assertEquals(expected, result);
    }
}
