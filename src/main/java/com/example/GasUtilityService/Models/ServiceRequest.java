package com.example.GasUtilityService.Models;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String details;

    private String status = "Pending";
    private String attachmentPath;

    private java.time.LocalDateTime submittedAt = java.time.LocalDateTime.now();
    private java.time.LocalDateTime resolvedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
