package com.jenia.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="USER")
@JsonIgnoreProperties({"posts"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", unique = true)
    private Integer id;

    @Column(name = "username",unique = true)
    @NotEmpty(message="provide valid user name")
    private String username;

    @Column(name = "lastname")
    @NotEmpty(message="provide valid last name")
    private String lastName;

    @Column(name = "password")

    @Length(min=5, message = "Your password must contain at least 5 chars")
    private String password;

    @Column(name = "email", unique = true)
    @Email(message="provide valid email")
    private String email;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="user_role",joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy="user")
    private Set<Post> posts = new HashSet<>();

    @Column(name="active")
    private Integer active;

    public User(
            final String username,
            final String lastName,
            final String password,
            final String email
    ) {
        this.username = username;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }

    public void addRole(final Role role) {
        roles.add(role);
    }
}
