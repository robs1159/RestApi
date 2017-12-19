package ca.ulaval.glo4002.crm.domain.users;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToMany
    private List<Role> roles;

    public Integer getId() {
        return id;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
