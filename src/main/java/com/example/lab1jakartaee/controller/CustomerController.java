package com.example.lab1jakartaee.controller;

import com.example.lab1jakartaee.entity.Customer;
import com.example.lab1jakartaee.repository.CustomerRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerController {

    @Inject
    CustomerRepository repository;

    @GET
    public List<Customer> getAll(){
        return repository.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getOne(@PathParam("id") Long id){
        var customer = repository.findOne(id);
        if(customer.isPresent())
            return Response.ok().entity(customer.get()).build();
        return Response.status(404).build();
    }

    @POST
    public Response addOne(Customer customer){
        repository.addCustomer(customer);
        return Response.created(URI.create("customers/" + customer.getId())).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOne(@PathParam("id") Long id){
        repository.deleteCustomer(id);
        return Response.status(202).build();
    }
}
