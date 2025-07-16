package com.example.springbootfirst.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data // Generates getters, setters, toString, equals, hashCode
@AllArgsConstructor // Generates constructor with all fields
@NoArgsConstructor // Generates constructor with no filed
@Entity
@Table(name="user_details")
public class RegisterDetails {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //- if you give this you should not give this field in input this will be auto generated
    private int empId;

//    nullable = false -> not null
    @Column(nullable = false)
    private String name;
    @Column(nullable = false , unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(name = "user_name" , nullable = false , unique = true)
    private String userName;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "empId"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId")
    )
    private Set<Roles> roles;
}
