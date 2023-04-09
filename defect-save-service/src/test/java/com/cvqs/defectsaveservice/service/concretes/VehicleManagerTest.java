package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.VehicleDto;
import com.cvqs.defectsaveservice.exception.EntityNotFoundException;
import com.cvqs.defectsaveservice.model.Vehicle;
import com.cvqs.defectsaveservice.repository.VehicleRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.modelmapper.ModelMapper;

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
        vehicle.setBrand("Audi");
        vehicle.setRegistrationPlate("34ba22");


        VehicleDto expectedResult = new VehicleDto("AUDI", "34BA23");

        Mockito.when(modelMapper.map(Mockito.any(VehicleDto.class), Mockito.eq(Vehicle.class))).thenReturn(vehicle);
        Mockito.when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        Mockito.when(modelMapper.map(Mockito.any(Vehicle.class), Mockito.eq(VehicleDto.class))).thenReturn(vehicleDto);

        VehicleDto result = vehicleManager.save(vehicleDto);
        Assertions.assertEquals(expectedResult, result);

        Mockito.verify(modelMapper).map(vehicleDto, Vehicle.class);
        Mockito.verify(vehicleRepository).save(vehicle);
        Mockito.verify(vehicleRepository, new Times(1)).save(Mockito.any(Vehicle.class));
        Mockito.verify(modelMapper, new Times(1)).map(Mockito.any(VehicleDto.class), Mockito.eq(Vehicle.class));
    }


    @DisplayName("should find Vehicle by RegistrationPlate and return Vehicle ")
    @Test
    void shouldFindVehicleByRegistrationPlateAndReturnVehicle() {
        String registrationPlate = "34BA23";
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("Audi");
        vehicle.setRegistrationPlate("34BA23");

        Vehicle expectedResult = new Vehicle();
        expectedResult.setBrand("Audi");
        expectedResult.setRegistrationPlate("34BA23");

        Mockito.when(vehicleRepository.findVehicleByRegistrationPlate(registrationPlate)).thenReturn(vehicle);

        Vehicle result = vehicleRepository.findVehicleByRegistrationPlate(registrationPlate);

        Assertions.assertEquals(expectedResult, result);
        Mockito.verify(vehicleRepository).findVehicleByRegistrationPlate(registrationPlate);
        Mockito.verify(vehicleRepository, new Times(1)).findVehicleByRegistrationPlate(registrationPlate);
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