package com.rainypeople.tmall;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Test01 {

    @Test
    public void test01(){

        Connection conn=null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/tmall_ssm?serverTimezone=UTC";
            String userName="root";
            String password="zc950816";
            conn= DriverManager.getConnection(url, userName, password);
            String sql="insert into category (name)values (?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,"钢铁侠");
            boolean flag = ps.execute();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
