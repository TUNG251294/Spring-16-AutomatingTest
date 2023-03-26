package com.codegym.cms;

import java.sql.*;
public class CreateH2DBTest {
    public static void main(String[] a)
            throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:h2:~/cms-v01-test", "sa", "");
        // add application code here
        conn.close();
    }
}
