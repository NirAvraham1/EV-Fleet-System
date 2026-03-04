package com.example.fleet_service.repository;

import com.example.fleet_service.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {
    Optional<Vehicle> findByLicensePlate(String licensePlate);
}