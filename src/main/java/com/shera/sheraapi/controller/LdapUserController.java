/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shera.sheraapi.controller;

import com.shera.sheraapi.model.AuthenBody;
import com.shera.sheraapi.model.LdapUser;
import com.shera.sheraapi.service.LdapUserService;
import com.shera.sheraapi.util.CustomErrorType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jirasak_ka
 */
@RestController
@RequestMapping("/ldap")
public class LdapUserController {

    public static final Logger logger = LoggerFactory.getLogger(LdapUserController.class);

    @Autowired
    LdapUserService ldapUserService;

    @RequestMapping(value = "/authen", method = RequestMethod.POST)
    public ResponseEntity<?> ldapAuthen(@RequestBody AuthenBody body) {
        logger.info("Try to authen by: " + body.getUsername());
        if (ldapUserService.checkLdapUserWasNotLocked(body.getUsername())) { //check user is not lock
            if (ldapUserService.ldapValidateLogin(body.getUsername(), body.getPassword()) && !"".equals(body.getPassword())) { //check validate user
                LdapUser ldapUser = ldapUserService.getLdapUserByUsername(body.getUsername());
                logger.info("User: " + body.getUsername() + " authen success!");
                return new ResponseEntity<>(ldapUser, HttpStatus.OK);
            }
            return new ResponseEntity(new CustomErrorType("ชื่อผู้ใช้ หรือ รหัสผ่านไม่ถูกต้อง",
                    "Username or password is invalid"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(new CustomErrorType("ผู้ใช้ " + body.getUsername() + " ถูกล็อค",
                "Username " + body.getUsername() + " is locked"), HttpStatus.LOCKED);
    }

    @RequestMapping(value = "/random100", method = RequestMethod.GET)
    public String random() {
        int num = (int) (Math.random() * 100);
        System.out.println("Random: " + num);
        return "" + num;
    }

    @RequestMapping(value = "/testpost", method = RequestMethod.POST)
    public String testPost() {
        Connection connect = null;
        Statement s = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connect = DriverManager.getConnection(""
                    + "jdbc:oracle:thin:@10.61.10.209:1521:mhpdb", "mhpadmin", "mhpsystem");

            s = connect.createStatement();

            String sql = "INSERT INTO test_users "
                    + "(users_name,users_password) "
                    + "VALUES ('asdfg','12345') ";
            s.execute(sql);

            System.out.println("Record Inserted Successfully");

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
        return "TEST POST";
    }

    @RequestMapping(value = "/testLoop", method = RequestMethod.POST)
    public ResponseEntity<?> testLoop(@RequestBody AuthenBody body) {

        return new ResponseEntity<>("user = " + body.getUsername() + ", pass = " + body.getPassword(), HttpStatus.OK);
    }
}
