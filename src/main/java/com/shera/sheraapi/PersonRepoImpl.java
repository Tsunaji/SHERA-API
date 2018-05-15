//package com.shera.sheraapi;
//
//import java.util.List;
//import javax.naming.directory.Attributes;
//import org.springframework.ldap.NamingException;
//import org.springframework.ldap.core.AttributesMapper;
//import org.springframework.ldap.core.LdapTemplate;
//import static org.springframework.ldap.query.LdapQueryBuilder.query;
//
//public class PersonRepoImpl implements PersonRepo {
//   private LdapTemplate ldapTemplate;
//
//   public void setLdapTemplate(LdapTemplate ldapTemplate) {
//      this.ldapTemplate = ldapTemplate;
//   }
//
//   public List<String> getAllPersonNames() {
//      return ldapTemplate.search(
//         query().where("objectclass").is("person"),
//         new AttributesMapper<String>() {
//            public String mapFromAttributes(Attributes attrs)
//               throws NamingException {
//               return attrs.get("cn").get().toString();
//            }
//         });
//   }
//}
