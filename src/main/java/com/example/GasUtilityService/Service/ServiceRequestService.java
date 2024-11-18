package com.example.GasUtilityService.Service;

import com.example.GasUtilityService.Models.ServiceRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceRequestService {
    public ServiceRequest submitRequest(ServiceRequest request);
    public List<ServiceRequest> getCustomerRequests(Long customerId);
    public ServiceRequest updateStatus(Long requestId, String status);
}
