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

    // שליפת כל המידע (עבור GET)
    public FleetStatusResponse getAllAssets() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<ChargingStation> stations = stationRepository.findAll();
        
        return FleetStatusResponse.builder()
                .vehicles(vehicles)
                .stations(stations)
                .build();
    }

    // הוספת רכב (עבור POST - Admin only)
    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    // הוספת עמדת טעינה (עבור POST - Admin only)
    public ChargingStation addStation(ChargingStation station) {
        return stationRepository.save(station);
    }
}