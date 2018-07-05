package com.shera.sheraapi.controller;

import com.shera.sheraapi.model.AuthenBody;
import com.shera.sheraapi.model.LdapUser;
import com.shera.sheraapi.service.LdapUserService;
import com.shera.sheraapi.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ldap")
public class LdapUserController {

    public static final Logger logger = LoggerFactory.getLogger(LdapUserController.class);

    @Autowired
    LdapUserService ldapUserService;

    @RequestMapping(value = "/authen", method = RequestMethod.POST)
    public ResponseEntity<?> user(@RequestBody AuthenBody body) {
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

    @RequestMapping(value = "/authenId", method = RequestMethod.POST)
    public ResponseEntity<?> userId(@RequestBody AuthenBody body) {
        logger.info("Try to authen by: " + body.getUsername());
        if (ldapUserService.checkLdapUserWasNotLocked(body.getUsername())) { //check user is not lock
            if (ldapUserService.ldapValidateLogin(body.getUsername(), body.getPassword()) && !"".equals(body.getPassword())) { //check validate user
                LdapUser ldapUser = ldapUserService.getLdapUserByUsername(body.getUsername());
                logger.info("User: " + body.getUsername() + " authen success!");
                return new ResponseEntity<>(ldapUser.getEmployeeID(), HttpStatus.OK);
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
}
