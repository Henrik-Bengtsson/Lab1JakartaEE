package com.example.lab1jakartaee.repository;

import com.example.lab1jakartaee.entity.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class CustomerRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<Customer> findAll() {
        Query query = entityManager.createQuery("SELECT c FROM Customer c");
        return (List<Customer>) query.getResultList();
    }

    public void addCustomer(Customer customer) {
        entityManager.persist(customer);
    }
}
