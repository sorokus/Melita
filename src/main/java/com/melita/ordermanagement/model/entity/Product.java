package com.melita.ordermanagement.model.entity;
/*
 * @author sorokus.dev@gmail.com
 */

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/*
 * @author sorokus.dev@gmail.com
 */

@Getter
@Setter
@Entity
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long   id;
    private String name;
    private String description;
    private Boolean isApprovable;

    @OneToMany(mappedBy = "product",
               fetch = FetchType.EAGER)
    private Set<Package> packages;

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name)
                && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

}
