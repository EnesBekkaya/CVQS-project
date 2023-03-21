package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.VehichleDto;
import com.cvqs.defectsaveservice.exception.EntityNotFoundException;
import com.cvqs.defectsaveservice.model.Vehichle;
import com.cvqs.defectsaveservice.repository.VehichleRepository;
import junit.framework.TestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.modelmapper.ModelMapper;


class VehichleManagerTest extends TestCase {
    private VehichleManager vehichleManager;
    private VehichleRepository vehichleRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        vehichleRepository = Mockito.mock(VehichleRepository.class);
        vehichleRepository = Mockito.mock(VehichleRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        vehichleManager = new VehichleManager(vehichleRepository, modelMapper);
    }

    @DisplayName("should save Vehicle and return vehicle dto")
    @Test
    void shouldSaveAndReturnVehicleDto() {
        VehichleDto vehichleDto = new VehichleDto("AUDI", "34BA23");
        Vehichle vehichle = new Vehichle();
        vehichle.setBrand("Audi");
        vehichle.setRegistrationPlate("34ba22");


        VehichleDto expectedResult = new VehichleDto("AUDI", "34BA23");

        Mockito.when(modelMapper.map(Mockito.any(VehichleDto.class), Mockito.eq(Vehichle.class))).thenReturn(vehichle);
        Mockito.when(vehichleRepository.save(vehichle)).thenReturn(vehichle);
        Mockito.when(modelMapper.map(Mockito.any(Vehichle.class), Mockito.eq(VehichleDto.class))).thenReturn(vehichleDto);

        VehichleDto result = vehichleManager.save(vehichleDto);
        assertEquals(expectedResult, result);

        Mockito.verify(modelMapper).map(vehichleDto, Vehichle.class);
        Mockito.verify(vehichleRepository).save(vehichle);
        Mockito.verify(vehichleRepository, new Times(1)).save(Mockito.any(Vehichle.class));
        Mockito.verify(modelMapper, new Times(1)).map(Mockito.any(VehichleDto.class), Mockito.eq(Vehichle.class));
    }


    @DisplayName("should find Vehicle by RegistrationPlate and return Vehichle ")
    @Test
    void shouldFindVehichleByRegistrationPlateAndReturnVehichle() {
        String registrationPlate = "34BA23";
        Vehichle vehichle = new Vehichle();
        vehichle.setBrand("Audi");
        vehichle.setRegistrationPlate("34BA23");

        Vehichle expectedResult = new Vehichle();
        expectedResult.setBrand("Audi");
        expectedResult.setRegistrationPlate("34BA23");

        Mockito.when(vehichleRepository.findVehichleByRegistrationPlate(registrationPlate)).thenReturn(vehichle);

        Vehichle result = vehichleRepository.findVehichleByRegistrationPlate(registrationPlate);

        assertEquals(expectedResult, result);
        Mockito.verify(vehichleRepository).findVehichleByRegistrationPlate(registrationPlate);
        Mockito.verify(vehichleRepository, new Times(1)).findVehichleByRegistrationPlate(registrationPlate);
    }

    @DisplayName("should throw EntityNotFoundException when the parameter of the findVehichleByRegistrationPlate RegistrationPlate does not exist ")
    @Test
    void shouldThrowEntityNotFoundException_whenRegistrationPlateDoesNotExist() {
        String registrationPlate = "34BA23";
        Vehichle vehichle = new Vehichle();
        vehichle.setBrand("Audi");
        vehichle.setRegistrationPlate("34BA23");

        Mockito.when(vehichleRepository.findVehichleByRegistrationPlate(registrationPlate)).thenReturn(null);

        EntityNotFoundException exception = null;
        try {
            vehichleManager.findVehichleByRegistrationPlate(registrationPlate);
        } catch (EntityNotFoundException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals("Plakaya ait Araç bulunamadı", exception.getMessage());

    }



        @AfterEach
    public void tearDown() {

    }
}