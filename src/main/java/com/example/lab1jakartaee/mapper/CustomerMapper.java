package com.example.lab1jakartaee.mapper;

import com.example.lab1jakartaee.dto.CustomerDto;
import com.example.lab1jakartaee.entity.Customer;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CustomerMapper {

    public List<CustomerDto> map(List<Customer> customers){
        return customers.stream().map(CustomerDto::new).toList();
    }

    public Customer map(CustomerDto customerDto){
        var c = new Customer();
        c.setId(customerDto.getId());
        c.setName(customerDto.getName());
        c.setSurname(customerDto.getSurname());
        c.setEmail(customerDto.getEmail());
        c.setPhoneNumber(customerDto.getPhoneNumber());
        return c;
    }

    public CustomerDto map(Customer customer){
        return new CustomerDto();
    }
}
