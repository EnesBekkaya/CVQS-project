package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.DefectDto;
import com.cvqs.defectsaveservice.dto.LocationDto;
import com.cvqs.defectsaveservice.exception.EntityNotFoundException;
import com.cvqs.defectsaveservice.model.Defect;
import com.cvqs.defectsaveservice.model.Image;
import com.cvqs.defectsaveservice.model.Location;
import com.cvqs.defectsaveservice.model.Vehicle;
import com.cvqs.defectsaveservice.service.abstracts.ImageService;
import com.cvqs.defectsaveservice.service.abstracts.LocationService;
import com.cvqs.defectsaveservice.service.abstracts.VehicleService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import com.cvqs.defectsaveservice.repository.DefectRepository;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class DefectManagerTest{
    private DefectManager defectManager;
   private  DefectRepository defectRepository;
   private LocationService locationService;
   private VehicleService vehicleService;
   private  ModelMapper modelMapper;
    private ImageService imageService;
    @BeforeEach
    public void setUp() {
        defectRepository = Mockito.mock(DefectRepository.class);
        locationService = Mockito.mock(LocationService.class);
        vehicleService = Mockito.mock(VehicleService.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        imageService=Mockito.mock(ImageService.class);
        defectManager = new DefectManager(defectRepository,locationService, vehicleService, imageService,modelMapper);
    }


    @DisplayName("should save Defect By DefectDto when NewDefect and return DefectDto")
    @Test
    void shouldSaveDefectDtoByDefectDtoWhenNewDefect() throws SQLException, IOException {
        MultipartFile file = new MockMultipartFile("test.jpg", new byte[]{});

        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationPlate("ABC123");

        DefectDto defectDto = new DefectDto();
        defectDto.setType("test-type");
        defectDto.setVehicle(vehicle);

        List<Location> locations=new ArrayList<>();
        Location location = new Location();
        location.setX(10);
        location.setY(25);
        locations.add(location);

        List<LocationDto> locationsDto = new ArrayList<>();

        LocationDto locationDto = new LocationDto();
        locationDto.setX(10);
        locationDto.setY(25);

        locationsDto.add(locationDto);
        defectDto.setLocations(locations);

        Defect defect=new Defect();
        defect.setLocations(locations);
        defect.setVehicle(vehicle);
        defect.setType("test-type");
        defect.setImage(new Image());

        DefectDto expectedResult=defectDto;

        Mockito.when(vehicleService.findVehicleByRegistrationPlate(vehicle.getRegistrationPlate())).thenReturn(vehicle);
        Mockito.when(defectRepository.getDefectsByTypeAndVehicle(defectDto.getType(), vehicle)).thenReturn(defect);
        Mockito.when(locationService.findLocationByXAndY(location.getX(), location.getY())).thenReturn(location);
        Mockito.when(modelMapper.map(defectRepository.save(defect),DefectDto.class)).thenReturn(defectDto);

        DefectDto result = defectManager.save(defectDto, file);

      Assertions.assertEquals(expectedResult,result);
    }
    @DisplayName("should save Defect By DefectDto when defect null and return defectDto")
    @Test
    void shouldSaveDefectDtoByDefectDtoWhenDefectNull() throws SQLException, IOException {
        MultipartFile file = new MockMultipartFile("test.jpg", new byte[]{});

        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationPlate("ABC123");

        DefectDto defectDto = new DefectDto();
        defectDto.setType("test-type");
        defectDto.setVehicle(vehicle);

        List<Location> locations=new ArrayList<>();
        Location location = new Location();
        location.setX(10);
        location.setY(25);
        locations.add(location);

        List<LocationDto> locationsDto = new ArrayList<>();

        LocationDto locationDto = new LocationDto();
        locationDto.setX(10);
        locationDto.setY(25);

        locationsDto.add(locationDto);
        defectDto.setLocations(locations);

        Defect defect=new Defect();
        defect.setLocations(locations);
        defect.setVehicle(vehicle);
        defect.setType("test-type");
        defect.setImage(new Image());

        DefectDto expectedResult=defectDto;

        Mockito.when(vehicleService.findVehicleByRegistrationPlate(vehicle.getRegistrationPlate())).thenReturn(vehicle);
        Mockito.when(defectRepository.getDefectsByTypeAndVehicle(defectDto.getType(), vehicle)).thenReturn(null);
        Mockito.when(locationService.findLocationByXAndY(location.getX(), location.getY())).thenReturn(location);
        Mockito.when(imageService.saveImage(file, locations)).thenReturn( new Image());
        Mockito.when(modelMapper.map(defectRepository.save(defect),DefectDto.class)).thenReturn(defectDto);

        DefectDto result = defectManager.save(defectDto, file);

        Assertions.assertEquals(expectedResult,result);
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

        Mockito.when(defectRepository.findAll()).thenReturn(defects);

        List<DefectDto> expectedDefectDtos = defects.stream()
                .map(defect -> modelMapper.map(defect, DefectDto.class))
                .collect(Collectors.toList());
        List<DefectDto> result = defectManager.getAll();

        Assertions.assertEquals(expectedDefectDtos, result);
        Mockito.verify(defectRepository).findAll();

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

        Mockito.when(defectRepository.findByRegistrationPlate(vehicle.getRegistrationPlate())).thenReturn(defects);

        List<DefectDto> result = defectManager.findByRegistrationPlate(vehicle.getRegistrationPlate());


        Assertions.assertEquals(expectedDefectDtos,result);
   }


    @DisplayName("should throw EntityNotFoundException when the parameter of the findVehicleByRegistrationPlate RegistrationPlate does not exist ")
    @Test
    void shouldThrowEntityNotFoundException_whenRegistrationPlateDoesNotExist() {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationPlate("ABC123");
        vehicle.setBrand("audi");

        List<Defect> defects = new ArrayList<>();

        Mockito.when(defectRepository.findByRegistrationPlate(vehicle.getRegistrationPlate())).thenReturn(defects);

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            defectManager.findByRegistrationPlate(vehicle.getRegistrationPlate());
        });

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Araca kayıtlı hata bulunamadı", exception.getMessage());
        Mockito.verify(defectRepository).findByRegistrationPlate(vehicle.getRegistrationPlate());
    }

    @DisplayName("should Return Byte By Plate With RegistrationPlate And DefectType")
    @Test
    void shouldReturnByteByPlateWithregistrationPlateAndDefectType() throws SQLException{
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
        Mockito.when(defectRepository.findDefectByregistrationPlateAndType(registrationPlate,defectType)).thenReturn(defect1);
        Mockito.when(imageService.getImage(image)).thenReturn(image);
        byte[] result=defectManager.getDefectImage(registrationPlate,defectType);
        Assertions.assertArrayEquals(expectedResult,result);
        Mockito.verify(defectRepository).findDefectByregistrationPlateAndType(registrationPlate,defectType);
        Mockito.verify(imageService).getImage(image);

    }

    @AfterEach
    public void tearDown() {

    }


}