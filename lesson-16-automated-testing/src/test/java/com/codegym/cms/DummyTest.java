package com.codegym.cms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DummyTest {
    @Test
    void assertionWorked() {
        int expected = 2;
        int actual = 1 + 1;
        assertEquals(expected, actual);
    }
}
