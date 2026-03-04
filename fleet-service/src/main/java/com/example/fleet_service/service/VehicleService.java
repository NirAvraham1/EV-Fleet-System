package com.example.fleet_service.service;

import com.example.fleet_service.model.Vehicle;
import com.example.fleet_service.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository repository;

    public Page<Vehicle> getAllVehicles(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        return repository.save(vehicle);
    }

    public void deleteVehicle(String id) {
        repository.deleteById(id);
    }

    public Vehicle updateStatus(String id, String status) {
        Vehicle vehicle = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicle.setStatus(status);
        return repository.save(vehicle);
    }
}