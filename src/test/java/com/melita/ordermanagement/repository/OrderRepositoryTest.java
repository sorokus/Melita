package com.melita.ordermanagement.repository;

import com.melita.ordermanagement.model.entity.Address;
import com.melita.ordermanagement.model.entity.Order;
import com.melita.ordermanagement.model.entity.Person;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Calendar;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * @author sorokus.dev@gmail.com
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    OrderRepository repository;

    @Test
    void findByIdAndApprovedByIsNull() {
        Optional<Order> order = repository.findByIdAndApprovedByIsNull(1L);
        assertThat(order).isNotNull().isEmpty();
    }

    @Test
    void findByAll() {
        Iterable<Order> orders = repository.findAll();
        assertThat(orders).isNotNull().hasSize(0);
    }

    @Test
    void saveNewOrder() {
        Order order = new Order();
        order.setPackageIds(Set.of(1L, 3L, 4L));
        Address address = new Address();
        order.setAddress(address);
        address.setProvince("Balzan1");
        address.setCity("Balzan1");
        address.setStreet("Triq Il-Kbira1");
        address.setApartment("11");
        address.setPostalCode("BZN 12591");
        Person person = new Person();
        order.setPerson(person);
        person.setFirstName("Zaren1");
        person.setLastName("Farrugia1");
        person.setMobileNo("+356 2152 3871");
        person.setEmail("z.farrugia121@mail.com");
        person.setPassportNo("0981481");

        Calendar currentTimeNow = Calendar.getInstance();
        currentTimeNow.add(Calendar.DAY_OF_YEAR, 2);
        order.setPrefDateFrom(currentTimeNow.getTime());
        currentTimeNow.add(Calendar.DAY_OF_YEAR, 3);
        order.setPrefDateTo(currentTimeNow.getTime());

        Order newOrder = repository.save(order);
        assertThat(newOrder).isNotNull();
        assertEquals(newOrder.getAddress().getStreet(), order.getAddress().getStreet());
        assertEquals(newOrder.getPerson().getLastName(), order.getPerson().getLastName());
    }

}