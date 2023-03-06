package com.example.lab1jakartaee.controller;

import com.example.lab1jakartaee.dto.CustomerDto;
import com.example.lab1jakartaee.entity.Customer;
import com.example.lab1jakartaee.mapper.CustomerMapper;
import com.example.lab1jakartaee.repository.CustomerRepository;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponse(responseCode = "200", description = "Returns list of customers",
            content = @Content(schema = @Schema(implementation = CustomerDto.class)))
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return customer",
                    content = @Content(schema = @Schema(implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "404", description = "Id not found")})
    public Response getOne(@PathParam("id") Long id){
        var customer = repository.findOne(id);
        if(customer.isPresent())
            return Response.ok().entity(customer.get()).build();
        throw new NotFoundException();
    }

    @POST
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax")})
    public Response addOne(Customer customer){
        repository.addCustomer(customer);
        return Response.created(URI.create("customers/" + customer.getId())).build();
    }

    @DELETE
    @Path("/{id}")
    @ApiResponse(responseCode = "200", description = "Customer deleted")
    public Response deleteOne(@PathParam("id") Long id){
        repository.deleteCustomer(id);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated",
                    content = @Content(schema = @Schema(implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "404", description = "Id not found")})
    public Response updateCustomer(@PathParam("id") Long id, CustomerDto customer){
        if(repository.findOne(id).isPresent()) {
            repository.update(id, customer);
            return Response.ok().build();
        }
        throw new NotFoundException();
    }
}
