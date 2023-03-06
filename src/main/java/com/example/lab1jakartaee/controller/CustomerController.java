package com.example.lab1jakartaee.controller;

import com.example.lab1jakartaee.dto.CustomerDto;
import com.example.lab1jakartaee.entity.Customer;
import com.example.lab1jakartaee.mapper.CustomerMapper;
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

    @Inject
    CustomerMapper customerMapper;

    @GET
    public List<CustomerDto> getAll(@QueryParam("name") String name, @QueryParam("surname") String surname){
        if(name == null && surname == null)
            return customerMapper.mapCustomer(repository.findAll());
        if(name != null && surname != null)
            return customerMapper.mapCustomer(repository.findByFullName(name, surname));
        if(name != null)
            return customerMapper.mapCustomer(repository.findByName(name));
        return customerMapper.mapCustomer(repository.findBySurname(surname));
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

    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") Long id, CustomerDto customer){
        repository.update(id, customer);
        return Response.ok().build();
    }
}
