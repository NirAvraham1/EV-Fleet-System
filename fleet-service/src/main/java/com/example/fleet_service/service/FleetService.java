package com.example.fleet_service.service;

import com.example.fleet_service.dto.FleetStatusResponse;
import com.example.fleet_service.model.ChargingStation;
import com.example.fleet_service.model.Vehicle;
import com.example.fleet_service.repository.ChargingStationRepository;
import com.example.fleet_service.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FleetService {

    private final VehicleRepository vehicleRepository;
    private final ChargingStationRepository stationRepository;

    public FleetStatusResponse getAllAssets() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<ChargingStation> stations = stationRepository.findAll();
        
        return FleetStatusResponse.builder()
                .vehicles(vehicles)
                .stations(stations)
                .build();
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public ChargingStation addStation(ChargingStation station) {
        return stationRepository.save(station);
    }
}