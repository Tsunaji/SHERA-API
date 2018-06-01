package com.shera.sheraapi.controller;

import com.shera.sheraapi.model.AuthenBody;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weight")
public class WeightController {

    public static final Logger logger = LoggerFactory.getLogger(WeightController.class);

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public String update(@RequestBody AuthenBody body) {
        Connection connect = null;
        Statement s = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connect = DriverManager.getConnection(""
                    + "jdbc:oracle:thin:@10.61.10.209:1521:mhpdb", "mhpadmin", "mhpsystem");

            s = connect.createStatement();

            String sql = "UPDATE test_users set USERS_PASSWORD = '" + body.getPassword() + "' where USERS_NAME = 'MF13-01'";
            s.execute(sql);

            System.out.println("Record Updated Successfully");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            s.close();
            connect.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return HttpStatus.OK.toString();
    }

    @RequestMapping(value = "/getWeight", method = RequestMethod.GET)
    public String getWeight() {
        Connection connect = null;
        Statement s = null;
        ResultSet rs = null;
        String password = "";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connect = DriverManager.getConnection(""
                    + "jdbc:oracle:thin:@10.61.10.209:1521:mhpdb", "mhpadmin", "mhpsystem");

            s = connect.createStatement();

            String sql = "SELECT * FROM test_users WHERE USERS_NAME = 'MF13-01'";

            rs = s.executeQuery(sql);

            while (rs.next()) {
                String username = rs.getString("USERS_NAME");
                password = rs.getString("USERS_PASSWORD");

                System.out.println(username + "\t" + password);
            }

//            System.out.println("Record Inserted Successfully");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            s.close();
            connect.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return password;
    }
}
