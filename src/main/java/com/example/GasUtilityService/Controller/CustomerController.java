package com.example.GasUtilityService.Controller;

import com.example.GasUtilityService.Models.Customer;
import com.example.GasUtilityService.Models.ServiceRequest;
import com.example.GasUtilityService.Repositories.CustomerRepository;
import com.example.GasUtilityService.Repositories.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/register")
    public Customer register(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @PostMapping("/{customerId}/requests")
    public ServiceRequest submitRequest(
            @PathVariable Long customerId,
            @RequestParam String type,
            @RequestParam String details,
            @RequestParam(required = false) MultipartFile attachment
    ) throws IOException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        ServiceRequest request = new ServiceRequest();
        request.setType(type);
        request.setDetails(details);
        request.setCustomer(customer);

        if (attachment != null && !attachment.isEmpty()) {
            validateFile(attachment);
            String uniqueFileName = UUID.randomUUID() + "_" + attachment.getOriginalFilename();
            File file = new File(UPLOAD_DIR + uniqueFileName);
            file.getParentFile().mkdirs();
            attachment.transferTo(file);
            request.setAttachmentPath(file.getAbsolutePath());
        }

        return serviceRequestRepository.save(request);
    }

    private void validateFile(MultipartFile file) {
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException("File size exceeds limit (5 MB)");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("Only image files are allowed");
        }
    }

    @GetMapping("/{customerId}/requests")
    public List<ServiceRequest> getRequests(@PathVariable Long customerId) {
        return serviceRequestRepository.findByCustomerId(customerId);
    }
}
