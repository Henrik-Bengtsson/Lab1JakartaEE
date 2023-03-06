package com.example.lab1jakartaee.repository;

import com.example.lab1jakartaee.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerRepositoryTest {

    @InjectMocks
    private CustomerRepository repository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    Customer cus1 = new Customer("Olle", "Larsson", "olleLarsson@gmail.com", "0712345678", 7812255352L);
    Customer cus2 = new Customer("Bert", "Jansson", "bertLarsson@gmail.com", "0787654321", 8011065452L);
    List<Customer> customerList = List.of(cus1, cus2);


    @Test
    void findAllShouldReturnListOfCustomer() {
        when(query.getResultList()).thenReturn(customerList);
        when(entityManager.createQuery("SELECT c FROM Customer c")).thenReturn(query);

        assertThat(repository.findAll()).isEqualTo(customerList);
    }

    @Test
    void findOneShouldReturnTrueIfCustomerExist() {
        when(entityManager.find(Customer.class, 1L)).thenReturn(cus1);

        assertThat(repository.findOne(1L)).contains(cus1);
    }

    @Test
    void findByNameShouldReturnCustomerOlle() {
        var name = "Olle";
        when(query.getResultList()).thenReturn(List.of(cus1));
        when(query.setParameter("name", name)).thenReturn(query);
        when(entityManager.createQuery("SELECT c FROM Customer c WHERE c.name like :name")).thenReturn(query);

        assertThat(repository.findByName(name)).isEqualTo(List.of(cus1));
    }

    @Test
    void findBySurnameShouldReturnCustomerJansson() {
        var surname = "Jansson";
        when(query.getResultList()).thenReturn(List.of(cus2));
        when(query.setParameter("surname", surname)).thenReturn(query);
        when(entityManager.createQuery("SELECT c FROM Customer c WHERE c.surname like :surname")).thenReturn(query);

        assertThat(repository.findBySurname(surname)).isEqualTo(List.of(cus2));
    }

    @Test
    void findByFullNameShouldReturnCustomerBertJansson() {
        var name = "Bert";
        var surname = "Jansson";
        when(query.getResultList()).thenReturn(List.of(cus2));
        when(query.setParameter("name", name)).thenReturn(query);
        when(query.setParameter("surname", surname)).thenReturn(query);
        when(entityManager.createQuery("SELECT c FROM Customer c WHERE c.name like :name And c.surname like :surname")).thenReturn(query);

        assertThat(repository.findByFullName(name, surname)).isEqualTo(List.of(cus2));
    }

    @Test
    void updateCustomerWithId1WithNewSurname() {
        var updatedCustomer = new Customer("Olle", "Svensson", "olleLarsson@gmail.com", "0712345678", 8406145457L);
        when(entityManager.find(Customer.class, 1L)).thenReturn(cus1);

        assertThat(repository.update(1L, updatedCustomer)).isEqualTo(cus1);
    }
}
