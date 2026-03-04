package com.example.fleet_service.repository;

import com.example.fleet_service.model.ChargingStation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChargingStationRepository extends MongoRepository<ChargingStation, String> {
}