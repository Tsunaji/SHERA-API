package com.shera.sheraapi.service;

import com.shera.sheraapi.model.Vehicle;
import java.util.List;

public interface WeightVehicleService {

    boolean registerVehicle(Vehicle vehicle);

    Vehicle getVehicle(int id);

    List<Vehicle> getAllVehicle();

    boolean updateVehicle(Vehicle vehicle, int id);

    boolean validateShipment(int id, String shipment);
}
