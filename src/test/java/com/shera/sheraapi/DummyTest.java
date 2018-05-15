package com.shera.sheraapi;

import java.util.List;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import static org.springframework.ldap.query.LdapQueryBuilder.query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration("classpath:**/app-context.xml")
public class DummyTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private LdapTemplate ldapTemplate;

    @Test
    public void testPopulated() {
        assertNotNull(ldapTemplate);
    }

    @Test
    public void testQuery() {
        List<Object> search = ldapTemplate.search(query().where("objectclass").is("person"),
                new AttributesMapper<Object>() {
            @Override
            public Object mapFromAttributes(Attributes atrbts) throws NamingException {
                return atrbts.get("cn").get();
            }
        });
        System.out.println(search);
    }
}
