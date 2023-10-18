package com.example.exercicio.service.serviceImpl;


import com.example.exercicio.entities.Address;
import com.example.exercicio.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public void createAddress(Address address) {
         addressRepository.save(address);
    }
}
