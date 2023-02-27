package com.example.lab1jakartaee.repository;

import com.example.lab1jakartaee.entity.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class CustomerRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<Customer> findAll() {
        Query query = entityManager.createQuery("SELECT c FROM Customer c");
        return (List<Customer>) query.getResultList();
    }

    public Optional<Customer> findOne(Long id) {
        return Optional.ofNullable(entityManager.find(Customer.class, id));
    }

    public void addCustomer(Customer customer) {
        entityManager.persist(customer);
    }

    public void deleteCustomer(Long id) {
        var customer = findOne(id);
        customer.ifPresent(c -> entityManager.remove(c));
    }

    public List<Customer> findByName(String name) {
        Query query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.name like :name");
        query.setParameter("name", name);
        return (List<Customer>) query.getResultList();
    }

    public List<Customer> findBySurname(String surname) {
        Query query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.surname LIKE :surname");
        query.setParameter("surname", surname);
        return (List<Customer>) query.getResultList();
    }

    public List<Customer> findByFullName(String name, String surname) {
        Query query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.name like :name And c.surname like :surname");
        query.setParameter("name", name);
        query.setParameter("surname", surname);
        return (List<Customer>) query.getResultList();
    }
}
