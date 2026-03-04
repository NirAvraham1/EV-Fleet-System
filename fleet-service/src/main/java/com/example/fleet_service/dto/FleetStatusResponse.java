package com.example.fleet_service.dto;

import com.example.fleet_service.model.ChargingStation;
import com.example.fleet_service.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FleetStatusResponse {
    private List<Vehicle> vehicles;
    private List<ChargingStation> stations;
}