package com.makiev.testtask.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "email")
public class Email {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "resource_id", nullable = false)
    @JsonIgnore
    private Resource resource;

    public Email (String email, Resource resource){
        this.email = email;
        this.resource = resource;
    }
}
