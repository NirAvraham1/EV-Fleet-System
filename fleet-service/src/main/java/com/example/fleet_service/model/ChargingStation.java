package com.example.fleet_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "charging_stations")
public class ChargingStation {
    @Id
    private String id;
    private String name;
    private String location; // GPS coordinates or Address
    private String type; // e.g., "DC Fast", "AC Level 2"
    private boolean isOccupied;
}