package com.melita.ordermanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/*
 * @author sorokus.dev@gmail.com
 */

@Getter
@Setter
@Entity
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    private Long product_id;

    private String description;

//    @ManyToMany(mappedBy = "packages")
//    @JsonIgnore
//    Set<Order> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Package aPackage = (Package) o;
        return Objects.equals(id, aPackage.id) && Objects.equals(product_id, aPackage.product_id)
                && Objects.equals(name, aPackage.name) && Objects.equals(description, aPackage.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, product_id, description);
    }
}
