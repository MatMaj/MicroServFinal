package com.microservice.MicroserviceProject.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//LOMBOK
//JPA
//VALIDATION JSR-303

@Entity
@Table(name="users")
@AllArgsConstructor @NoArgsConstructor
public class User {

    @Id
    @Column(name="EMAIL")
    @Getter @Setter
    @NotNull @NotBlank @NotEmpty
    private String email;

    /*public String getEmail()
    {
        return this.email;
    }
    public void setEmail(String value)
    {
        this.email = value;
    }*/

    @Column(name="NAME")
    @Getter @Setter
    @NotNull @NotBlank @NotEmpty
    private String name;

    /*public String getName()
    {
        return this.name;
    }
    public void setName(String value)
    {
        this.name = value;
    }*/

    @Column(name="PASSWORD")
    @Getter @Setter
    @NotNull @NotBlank @NotEmpty
    private String password;

    /*public String getPassword()
    {
        return this.password;
    }
    public void setPassword(String value)
    {
        this.password = value;
    }*/

}
