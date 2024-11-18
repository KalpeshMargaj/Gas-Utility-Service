package com.example.GasUtilityService.Service.Implementation;

import com.example.GasUtilityService.Models.ServiceRequest;
import com.example.GasUtilityService.Repositories.ServiceRequestRepository;
import com.example.GasUtilityService.Service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceRequestImpl implements ServiceRequestService {
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public ServiceRequest submitRequest(ServiceRequest request) {
        return serviceRequestRepository.save(request);
    }

    public List<ServiceRequest> getCustomerRequests(Long customerId) {
        return serviceRequestRepository.findByCustomerId(customerId);
    }

    public ServiceRequest updateStatus(Long requestId, String status) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(status);
        if ("Resolved".equalsIgnoreCase(status)) {
            request.setResolvedAt(java.time.LocalDateTime.now());
        }
        return serviceRequestRepository.save(request);
    }
}
