package com.melita.ordermanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

/*
 * @author sorokus.dev@gmail.com
 */

@Getter
@Setter
@Entity
public class Person {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long   id;

    private String firstName;
    private String lastName;
    private String mobileNo;
    private String email;
    private String passportNo;

}
