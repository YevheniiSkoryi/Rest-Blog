package com.jenia.blog.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Setter
@Getter
@Entity
@Table(name = "ROLE")
@JsonIgnoreProperties({"users"})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Integer id;
    @Column(name = "role", unique = true)
    private String role;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "roles")
    private Collection<User> users;


}
