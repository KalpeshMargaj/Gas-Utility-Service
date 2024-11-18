package com.example.GasUtilityService.Controller;

import com.example.GasUtilityService.Models.ServiceRequest;
import com.example.GasUtilityService.Repositories.ServiceRequestRepository;
import com.example.GasUtilityService.Service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @GetMapping("/requests")
    public List<ServiceRequest> getAllRequests() {
        return serviceRequestRepository.findAll();
    }

    @GetMapping("/requests/{requestId}/attachment")
    public byte[] getAttachment(@PathVariable Long requestId) throws IOException {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (request.getAttachmentPath() == null) {
            throw new RuntimeException("No attachment found for this request");
        }

        Path path = Paths.get(request.getAttachmentPath());
        return Files.readAllBytes(path);
    }
}
