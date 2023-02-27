package com.example.lab1jakartaee.controller;

import com.example.lab1jakartaee.entity.Customer;
import com.example.lab1jakartaee.repository.CustomerRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerController {

    @Inject
    CustomerRepository repository;

    @GET
    public List<Customer> getAll(){
        return repository.findAll();
    }
}
