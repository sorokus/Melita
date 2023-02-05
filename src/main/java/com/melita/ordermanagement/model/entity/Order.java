package com.melita.ordermanagement.model.entity;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/*
 * @author sorokus.dev@gmail.com
 */

@Getter
@Setter
@Entity
@Table(name = "\"order\"")
public class Order {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    //    @ManyToMany
    //    @JoinTable(
    //            name = "order_package",
    //            joinColumns = @JoinColumn(name = "order_id"),
    //            inverseJoinColumns = @JoinColumn(name = "package_id"))
    //    private Set<Package> packages;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    private Person person;

    private Date   prefDateFrom;
    private Date   prefDateTo;
    private Date   submittedAt = new Date();
    private Date   approvedAt;
    private String approvedBy;

}
