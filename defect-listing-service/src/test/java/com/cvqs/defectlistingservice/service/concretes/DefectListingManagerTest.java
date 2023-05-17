package com.cvqs.defectlistingservice.service.concretes;

import com.cvqs.defectlistingservice.dto.DefectDto;
import com.cvqs.defectlistingservice.exception.EntityNotFoundException;
import com.cvqs.defectlistingservice.model.Defect;
import com.cvqs.defectlistingservice.model.Image;
import com.cvqs.defectlistingservice.model.Location;
import com.cvqs.defectlistingservice.model.Vehicle;
import com.cvqs.defectlistingservice.repository.DefectListingRepository;
import com.cvqs.defectlistingservice.service.abstracts.ImageService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class DefectListingManagerTest  {
    private DefectListingManager defectListingManager;
    private  DefectListingRepository defectListingRepository;
    private  ModelMapper modelMapper;
    private  ImageService imageService;
    @BeforeEach
    public void setUp() {
        defectListingRepository = Mockito.mock(DefectListingRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        imageService = Mockito.mock(ImageService.class);

        defectListingManager = new DefectListingManager(defectListingRepository,modelMapper, imageService);
    }
    @DisplayName("should return defect list")
     @Test
     void shouldReturnDefectList(){
         Location location=new Location();
         location.setX(100);
         location.setY(200);
         Location location2=new Location();
         location2.setX(3);
         location2.setY(23);

         List<Location> locations = new ArrayList<>(Arrays.asList(location,location2));


         Vehicle vehicle = new Vehicle();
         vehicle.setRegistrationPlate("ABC123");
         Defect defect1=new Defect();
         defect1.setLocations(locations);
         defect1.setVehicle(vehicle);
         defect1.setType("çizik");
         Defect defect2=new Defect();
         defect2.setLocations(locations);
         defect2.setVehicle(vehicle);
         defect2.setType("göçük");
         List<Defect> defects = Arrays.asList(defect1, defect2);

         Mockito.when(defectListingRepository.findAll()).thenReturn(defects);

         List<DefectDto> expectedDefectDtos = defects.stream()
                 .map(defect -> modelMapper.map(defect, DefectDto.class))
                 .collect(Collectors.toList());
         List<DefectDto> result = defectListingManager.getAll();

         Assertions.assertEquals(expectedDefectDtos, result);
         Mockito.verify(defectListingRepository).findAll();

     }
     @DisplayName("should find Defect by RegistrationPlate and return DefectDto ")
     @Test
    void shouldFindDefectByRegistrationPlateAndReturnDefectDto(){
         Location location=new Location();
         location.setX(100);
         location.setY(200);
         Location location2=new Location();
         location2.setX(3);
         location2.setY(23);

         List<Location> locations = new ArrayList<>(Arrays.asList(location,location2));
         Vehicle vehicle = new Vehicle();
         vehicle.setRegistrationPlate("ABC123");

         Defect defect1=new Defect();
         defect1.setLocations(locations);
         defect1.setVehicle(vehicle);
         defect1.setType("çizik");
         Defect defect2=new Defect();
         defect2.setLocations(locations);
         defect2.setVehicle(vehicle);
         defect2.setType("göçük");
         List<Defect> defects = Arrays.asList(defect1, defect2);
         List<DefectDto> expectedDefectDtos = defects.stream()
                 .map(defect -> modelMapper.map(defect, DefectDto.class))
                 .collect(Collectors.toList());

         Mockito.when(defectListingRepository.findByRegistrationPlate(vehicle.getRegistrationPlate())).thenReturn(defects);

         List<DefectDto> result = defectListingManager.findByRegistrationPlate(vehicle.getRegistrationPlate());


         Assertions.assertEquals(expectedDefectDtos,result);
    }


     @DisplayName("should throw EntityNotFoundException when the parameter of the findVehicleByRegistrationPlate RegistrationPlate does not exist ")
     @Test
     void shouldThrowEntityNotFoundException_whenRegistrationPlateDoesNotExist() {
         Vehicle vehicle = new Vehicle();
         vehicle.setRegistrationPlate("ABC123");
         vehicle.setBrand("audi");

         List<Defect> defects = new ArrayList<>();

         Mockito.when(defectListingRepository.findByRegistrationPlate(vehicle.getRegistrationPlate())).thenReturn(defects);

         EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
             defectListingManager.findByRegistrationPlate(vehicle.getRegistrationPlate());
         });

         Assertions.assertNotNull(exception);
         Assertions.assertEquals("No defect found for this vehicle.", exception.getMessage());
         Mockito.verify(defectListingRepository).findByRegistrationPlate(vehicle.getRegistrationPlate());
     }

     @DisplayName("should Return Byte By Plate With RegistrationPlate And DefectType")
     @Test
     void shouldReturnByteByPlateWithregistrationPlateAndDefectType() throws SQLException {
         String registrationPlate="34BA23";
         String defectType="çizik";
         Location location=new Location();
         location.setX(100);
         location.setY(200);
         Location location2=new Location();
         location2.setX(3);
         location2.setY(23);

         List<Location> locations = new ArrayList<>(Arrays.asList(location,location2));
         Vehicle vehicle = new Vehicle();
         byte[] data= "test-image".getBytes();
         Image image=new Image();
         image.setData(new SerialBlob(data));
         vehicle.setRegistrationPlate("34BA23");
         Defect defect1=new Defect();
         defect1.setLocations(locations);
         defect1.setVehicle(vehicle);
         defect1.setType("çizik");
         defect1.setImage(image);
         byte[] expectedResult= data;
         Mockito.when(defectListingRepository.findDefectByregistrationPlateAndType(registrationPlate,defectType)).thenReturn(defect1);
         Mockito.when(imageService.getImage(image)).thenReturn(image);
         byte[] result=defectListingManager.getDefectImage(registrationPlate,defectType);
         Assertions.assertArrayEquals(expectedResult,result);
         Mockito.verify(defectListingRepository).findDefectByregistrationPlateAndType(registrationPlate,defectType);
         Mockito.verify(imageService).getImage(image);

     }
    @DisplayName("should Return Byte By Plate With RegistrationPlate And DefectType")
    @Test
    void shouldThrowEntityNotFoundExceptionByRegistrationPlateAndDefectType() throws SQLException {
        String registrationPlate="34BA23";
        String defectType="çizik";
        Location location=new Location();
        location.setX(100);
        location.setY(200);
        Location location2=new Location();
        location2.setX(3);
        location2.setY(23);

        List<Location> locations = new ArrayList<>(Arrays.asList(location,location2));
        Vehicle vehicle = new Vehicle();
        byte[] data= "test-image".getBytes();
        Image image=new Image();
        image.setData(new SerialBlob(data));
        vehicle.setRegistrationPlate("34BA23");
        Defect defect1=new Defect();
        defect1.setLocations(locations);
        defect1.setVehicle(vehicle);
        defect1.setType("çizik");
        defect1.setImage(image);
        byte[] expectedResult= data;
        Mockito.when(defectListingRepository.findDefectByregistrationPlateAndType(registrationPlate,defectType)).thenReturn(null);
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            defectListingManager.getDefectImage(registrationPlate,defectType);
        });

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("No defect records found for the vehicle with the license plate code "+registrationPlate, exception.getMessage());
        Mockito.verify(defectListingRepository).findDefectByregistrationPlateAndType(registrationPlate,defectType);

    }
     @DisplayName("should Return List Of DefectDto By pageNo And pageSize And sortBy")
     @Test
     void shouldReturnListOfDefectDtoBypageNoAndpageSizeAndsortBy() {
         DefectDto defectDto1 = new DefectDto();
         DefectDto defectDto2 = new DefectDto();
         Location location=new Location();
         location.setX(100);
         location.setY(200);
         Location location2=new Location();
         location2.setX(3);
         location2.setY(23);

         List<Location> locations = new ArrayList<>(Arrays.asList(location,location2));
         Vehicle vehicle = new Vehicle();
         vehicle.setRegistrationPlate("ABC123");

         Defect defect1=new Defect();
         defect1.setLocations(locations);
         defect1.setVehicle(vehicle);
         defect1.setType("çizik");
         Defect defect2=new Defect();
         defect2.setLocations(locations);
         defect2.setVehicle(vehicle);
         defect2.setType("göçük");
         List<Defect> defects = Arrays.asList(defect1, defect2);
         PageImpl<Defect> page = new PageImpl<>(defects);

         List<DefectDto> expectedResult=Arrays.asList(defectDto1,defectDto2);
         Mockito.when(defectListingRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);
         Mockito.when(modelMapper.map(defect1, DefectDto.class)).thenReturn(defectDto1);
         Mockito.when(modelMapper.map(defect2, DefectDto.class)).thenReturn(defectDto2);

         List<DefectDto> result=defectListingManager.getDefectSorted(0,2,"type");

         Assertions.assertEquals(expectedResult,result);

         Mockito.verify(defectListingRepository).findAll(Mockito.any(Pageable.class));


     }
     @AfterEach
    public void tearDown() {

     }

}