/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shera.sheraapi;

import java.util.List;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import org.junit.Test;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 *
 * @author jirasak_ka
 */
public class AuthenTest {

//    @Autowired
    private LdapContextSource contextSource;
    private LdapTemplate ldapTemplate;

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public List<String> getAllPersonNames() {
        return ldapTemplate.search(
                query().where("objectCategory").is("user").and("sAMAccountName").is("jirasak_ka"),
                new AttributesMapper<String>() {
            public String mapFromAttributes(Attributes attrs)
                    throws NamingException {
                return attrs.get("cn").get().toString();
            }
        });
    }

    @Test
    public void run() {
        List<String> strings = getAllPersonNames();
        if (strings == null) {
            System.out.println("null");
        } else {
            System.out.println("not null");
        }
    }
}
