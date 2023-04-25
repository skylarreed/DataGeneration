package com.sr.datagen.models;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;





@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("User")
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @XStreamAlias("userId")
    private long userId;

    @Column(name = "first_name")
    @XStreamAlias("firstName")
    private String firstName;

    @Column(name = "last_name")
    @XStreamAlias("lastName")
    private String lastName;

    @Column(name = "email")
    @XStreamAlias("email")
    private String email;

}
