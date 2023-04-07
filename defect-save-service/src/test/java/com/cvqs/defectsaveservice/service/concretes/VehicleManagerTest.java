package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.VehichleDto;
import com.cvqs.defectsaveservice.exception.EntityNotFoundException;
import com.cvqs.defectsaveservice.model.Vehicle;
import com.cvqs.defectsaveservice.repository.VehichleRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


class VehicleManagerTest {
    private VehicleManager vehichleManager;
    private VehichleRepository vehichleRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        vehichleRepository = Mockito.mock(VehichleRepository.class);
        vehichleRepository = Mockito.mock(VehichleRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        vehichleManager = new VehicleManager(vehichleRepository, modelMapper);
    }

    @DisplayName("should save Vehicle and return vehicle dto")
    @Test
    void shouldSaveAndReturnVehicleDto() {
        VehichleDto vehichleDto = new VehichleDto("AUDI", "34BA23");
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("Audi");
        vehicle.setRegistrationPlate("34ba22");


        VehichleDto expectedResult = new VehichleDto("AUDI", "34BA23");

        Mockito.when(modelMapper.map(Mockito.any(VehichleDto.class), Mockito.eq(Vehicle.class))).thenReturn(vehicle);
        Mockito.when(vehichleRepository.save(vehicle)).thenReturn(vehicle);
        Mockito.when(modelMapper.map(Mockito.any(Vehicle.class), Mockito.eq(VehichleDto.class))).thenReturn(vehichleDto);

        VehichleDto result = vehichleManager.save(vehichleDto);
        Assertions.assertEquals(expectedResult, result);

        Mockito.verify(modelMapper).map(vehichleDto, Vehicle.class);
        Mockito.verify(vehichleRepository).save(vehicle);
        Mockito.verify(vehichleRepository, new Times(1)).save(Mockito.any(Vehicle.class));
        Mockito.verify(modelMapper, new Times(1)).map(Mockito.any(VehichleDto.class), Mockito.eq(Vehicle.class));
    }


    @DisplayName("should find Vehicle by RegistrationPlate and return Vehichle ")
    @Test
    void shouldFindVehichleByRegistrationPlateAndReturnVehichle() {
        String registrationPlate = "34BA23";
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("Audi");
        vehicle.setRegistrationPlate("34BA23");

        Vehicle expectedResult = new Vehicle();
        expectedResult.setBrand("Audi");
        expectedResult.setRegistrationPlate("34BA23");

        Mockito.when(vehichleRepository.findVehichleByRegistrationPlate(registrationPlate)).thenReturn(vehicle);

        Vehicle result = vehichleRepository.findVehichleByRegistrationPlate(registrationPlate);

        Assertions.assertEquals(expectedResult, result);
        Mockito.verify(vehichleRepository).findVehichleByRegistrationPlate(registrationPlate);
        Mockito.verify(vehichleRepository, new Times(1)).findVehichleByRegistrationPlate(registrationPlate);
    }

    @DisplayName("should throw EntityNotFoundException when the parameter of the findVehichleByRegistrationPlate RegistrationPlate does not exist ")
    @Test
    void shouldThrowEntityNotFoundException_whenRegistrationPlateDoesNotExist() {
        String registrationPlate = "34BA23";
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("Audi");
        vehicle.setRegistrationPlate("34BA23");

        Mockito.when(vehichleRepository.findVehichleByRegistrationPlate(registrationPlate)).thenReturn(null);

        EntityNotFoundException exception = null;
        try {
            vehichleManager.findVehichleByRegistrationPlate(registrationPlate);
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
        List<VehichleDto> expectedResult= vehicles.stream().map(defect1 -> modelMapper.map(defect1,VehichleDto.class)).collect(Collectors.toList());
        Mockito.when(vehichleRepository.findAll()).thenReturn(vehicles);
        List<VehichleDto> result=vehichleManager.getAll();

        Assertions.assertEquals(expectedResult,result);
        Mockito.verify(vehichleRepository).findAll();

    }

        @AfterEach
    public void tearDown() {

    }
}