package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.DefectDto;
import com.cvqs.defectsaveservice.dto.VehicleDto;
import com.cvqs.defectsaveservice.exception.EntityNotFoundException;
import com.cvqs.defectsaveservice.model.Vehicle;
import com.cvqs.defectsaveservice.repository.VehicleRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


class VehicleManagerTest {
    private VehicleManager vehicleManager;
    private VehicleRepository vehicleRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        vehicleRepository = Mockito.mock(VehicleRepository.class);
        vehicleRepository = Mockito.mock(VehicleRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        vehicleManager = new VehicleManager(vehicleRepository, modelMapper);
    }

    @DisplayName("should save Vehicle and return vehicle dto")
    @Test
    void shouldSaveAndReturnVehicleDto() {
        VehicleDto vehicleDto = new VehicleDto("AUDI", "34BA23");
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("AUDİ");
        vehicle.setRegistrationPlate("34BA23");


        VehicleDto expectedResult = new VehicleDto("AUDI", "34BA23");

        Mockito.when(vehicleRepository.findVehicleByRegistrationPlate(vehicle.getRegistrationPlate())).thenReturn(null);
        Mockito.when(modelMapper.map(vehicleRepository.save(vehicle), VehicleDto.class)).thenReturn(vehicleDto);

        VehicleDto result = vehicleManager.save(vehicleDto);
        Assertions.assertEquals(expectedResult, result);
        Mockito.verify(vehicleRepository).findVehicleByRegistrationPlate(vehicle.getRegistrationPlate());

    }

    @DisplayName("should Throw ResponseStatusException And Return Exception")
    @Test
    void shouldThrowResponseStatusExceptionAndReturnException() {
        VehicleDto vehicleDto = new VehicleDto("AUDI", "34BA23");
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("AUDİ");
        vehicle.setRegistrationPlate("34BA23");

        Mockito.when(vehicleRepository.findVehicleByRegistrationPlate(vehicle.getRegistrationPlate())).thenReturn(vehicle);
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            vehicleManager.save(vehicleDto);
        });

        Assertions.assertNotNull(exception);
        Assertions.assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());

    }
    @DisplayName("should find Vehicle by RegistrationPlate and return Vehicle ")
    @Test
    void shouldFindVehicleByRegistrationPlateAndReturnVehicle() {
        String registrationPlate = "34BA23";
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("Audi");
        vehicle.setRegistrationPlate("34BA23");

        Vehicle expectedResult = vehicle;


        Mockito.when(vehicleRepository.findVehicleByRegistrationPlate(registrationPlate)).thenReturn(vehicle);

        Vehicle result = vehicleManager.findVehicleByRegistrationPlate(registrationPlate);

        Assertions.assertEquals(expectedResult, result);
        Mockito.verify(vehicleRepository).findVehicleByRegistrationPlate(registrationPlate);
    }

    @DisplayName("should throw EntityNotFoundException when the parameter of the findVehicleByRegistrationPlate RegistrationPlate does not exist ")
    @Test
    void shouldThrowEntityNotFoundException_whenRegistrationPlateDoesNotExist() {
        String registrationPlate = "34BA23";
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("Audi");
        vehicle.setRegistrationPlate("34BA23");

        Mockito.when(vehicleRepository.findVehicleByRegistrationPlate(registrationPlate)).thenReturn(null);

        EntityNotFoundException exception = null;
        try {
            vehicleManager.findVehicleByRegistrationPlate(registrationPlate);
        } catch (EntityNotFoundException e) {
            exception = e;
        }

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Plakaya ait Araç bulunamadı", exception.getMessage());

    }

    @DisplayName("should Get All Vehicle Dto List ")
    @Test
    void shouldGetAllVehicleDtoList() {
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("Audi");
        vehicle.setRegistrationPlate("34BA23");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setBrand("bmw");
        vehicle2.setRegistrationPlate("34AD12");

        List<Vehicle> vehicles =new ArrayList<>(Arrays.asList(vehicle, vehicle2));
        List<VehicleDto> expectedResult= vehicles.stream().map(defect1 -> modelMapper.map(defect1, VehicleDto.class)).collect(Collectors.toList());
        Mockito.when(vehicleRepository.findAll()).thenReturn(vehicles);
        List<VehicleDto> result=vehicleManager.getAll();

        Assertions.assertEquals(expectedResult,result);
        Mockito.verify(vehicleRepository).findAll();

    }

        @AfterEach
    public void tearDown() {

    }
}