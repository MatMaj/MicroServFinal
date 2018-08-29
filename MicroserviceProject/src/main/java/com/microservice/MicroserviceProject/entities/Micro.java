package com.microservice.MicroserviceProject.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="micro")
@AllArgsConstructor @NoArgsConstructor
public class Micro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    @Getter @Setter
    private Integer id;

    @Column(name="DESCRIPTION")
    @Getter @Setter
    @NotNull @NotBlank @NotEmpty
    private String description;

    @Column(name="DATE")
    @Getter @Setter
    private Date date;

    @Column(name="PRIORITY")
    @Getter @Setter
    @NotEmpty @NotBlank @NotNull
    private String priority;

    @Column(name="FK_USER")
    @Getter @Setter
    @NotEmpty @NotBlank @NotNull
    private String fkUser;

    @PrePersist
    void getTimeOperation(){
        this.date = new Date();
    }
}
