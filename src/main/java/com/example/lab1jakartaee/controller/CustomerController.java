package com.example.lab1jakartaee.controller;

import com.example.lab1jakartaee.entity.Customer;
import com.example.lab1jakartaee.repository.CustomerRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
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

    @POST
    public Response addOne(Customer customer){
        repository.addCustomer(customer);
        return Response.created(URI.create("customers/" + customer.getId())).build();
    }
}
