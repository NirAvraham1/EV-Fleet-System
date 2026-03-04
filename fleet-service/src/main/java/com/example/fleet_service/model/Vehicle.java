package com.example.fleet_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vehicles")
public class Vehicle {
    @Id
    private String id;
    private String licensePlate;
    private String model;
    private String status; // AVAILABLE, IN_USE, CHARGING, MAINTENANCE
    private Integer batteryLevel;
    private String location;
}