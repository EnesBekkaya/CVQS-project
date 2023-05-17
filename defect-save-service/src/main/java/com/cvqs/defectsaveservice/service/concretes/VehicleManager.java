package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.VehicleDto;
import com.cvqs.defectsaveservice.exception.EntityNotFoundException;
import com.cvqs.defectsaveservice.model.Vehicle;
import com.cvqs.defectsaveservice.repository.VehicleRepository;
import com.cvqs.defectsaveservice.service.abstracts.VehicleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
/**
 * The VehicleManager class is derived from the VehicleService interface and
 * manages operations related to vehicles. This class uses VehicleRepository objects for database operations.
 *
 * @author Enes Bekkaya
 * @since  13.02.2023
 */
@Service
@RequiredArgsConstructor
public class VehicleManager implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper;

    /**
     * Creates a Vehicle object using the given VehicleDto object and saves it to the database.
     * Throws a ResponseStatusException if a Vehicle object with the same registration plate already exists.
     * @param vehicleDto the VehicleDto object to be saved
     * @throws ResponseStatusException if a Vehicle object with the same registration plate already exists
     * @return the saved VehicleDto object
     *
     */
    @Override
    public VehicleDto save(VehicleDto vehicleDto) {
        if (vehicleRepository.findVehicleByRegistrationPlate(vehicleDto.getRegistrationPlate()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Bu plaka koduna sahip araç zaten kayıtlı.");
        }
        Vehicle vehicle =modelMapper.map(vehicleDto, Vehicle.class);
        return modelMapper.map(vehicleRepository.save(vehicle), VehicleDto.class);
    }

    /**
     * Finds all Vehicle objects in the database, converts them to VehicleDto objects, and returns them as a list.
     * @return a list containing VehicleDto counterparts of all Vehicle objects in the database
     */
    @Override
    public List<VehicleDto> getAll() {
        List <Vehicle> vehicles = vehicleRepository.findAll();
        List<VehicleDto> vehicleDtos = vehicles.stream().map(defect1 -> modelMapper.map(defect1, VehicleDto.class)).collect(Collectors.toList());
        return vehicleDtos;
    }

    /**
     * Finds a Vehicle object with the given registration plate.
     * @param registrationPlate registration plate of the Vehicle object to be found
     * @return the Vehicle object with the given registration plate
     * @throws EntityNotFoundException if a Vehicle object with the given registration plate cannot be found
     *
     */
    @Override
    public Vehicle findVehicleByRegistrationPlate(String registrationPlate) {
        Vehicle vehicle = vehicleRepository.findVehicleByRegistrationPlate(registrationPlate);
        if(vehicle ==null) {
            throw new EntityNotFoundException("Operation failed!! No such vehicle with the registration plate "+registrationPlate);

        }
        return vehicle;
    }
}
