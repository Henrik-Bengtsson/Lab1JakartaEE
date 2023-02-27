package com.example.lab1jakartaee.mapper;

import com.example.lab1jakartaee.dto.CustomerDto;
import com.example.lab1jakartaee.entity.Customer;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CustomerMapper {

    public List<CustomerDto> mapCustomer(List<Customer> customers){
        return customers.stream().map(customer -> new CustomerDto(
                customer.getId(), customer.getName(), customer.getSurname(),
                customer.getEmail(), customer.getPhoneNumber())).toList();
    }
}
