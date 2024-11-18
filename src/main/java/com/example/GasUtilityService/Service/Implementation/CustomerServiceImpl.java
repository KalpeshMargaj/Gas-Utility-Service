package com.example.GasUtilityService.Service.Implementation;
import com.example.GasUtilityService.Repositories.CustomerRepository;
import com.example.GasUtilityService.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
}
