package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.DefectDto;
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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



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


    @DisplayName("should Save DefectDto By DefectDto When Defect Null")
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
      Mockito.verify(vehicleService).findVehicleByRegistrationPlate(vehicle.getRegistrationPlate());
      Mockito.verify(defectRepository).getDefectsByTypeAndVehicle(defectDto.getType(),vehicle);
      Mockito.verify(locationService).findLocationByXAndY(location.getX(), location.getY());
      Mockito.verify(imageService).saveImage(file,locations);
    }
    @DisplayName("should Throw EntityNotFoundException By Defect Not Null")
    @Test
    void shouldThrowEntityNotFoundExceptionByDefectNotNull() throws SQLException, IOException {
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

        defectDto.setLocations(locations);

        Defect defect=new Defect();
        defect.setLocations(locations);
        defect.setVehicle(vehicle);
        defect.setType("test-type");
        defect.setImage(new Image());


        Mockito.when(vehicleService.findVehicleByRegistrationPlate(vehicle.getRegistrationPlate())).thenReturn(vehicle);
        Mockito.when(defectRepository.getDefectsByTypeAndVehicle(defectDto.getType(), vehicle)).thenReturn(defect);
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            defectManager.save(defectDto,file);
        });

        Assertions.assertNotNull(exception);
        Assertions.assertEquals(defectDto.getVehicle().getRegistrationPlate() +
                " plaka kodlu araç için böyle bir hata kayıtlıdır.", exception.getMessage());
        Mockito.verify(vehicleService).findVehicleByRegistrationPlate(vehicle.getRegistrationPlate());
        Mockito.verify(defectRepository).getDefectsByTypeAndVehicle(defectDto.getType(),vehicle);
    }

    @DisplayName("should update Defect By DefectDto when defect not null and return defectDto")
    @Test
    void shouldUpdateDefectByDefectDtoWhenDefectNotNull() throws SQLException, IOException {
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
        Mockito.when(imageService.saveImage(file, locations)).thenReturn( new Image());
        Mockito.when(modelMapper.map(defectRepository.save(defect),DefectDto.class)).thenReturn(defectDto);

        DefectDto result = defectManager.update(defectDto, file);

        Assertions.assertEquals(expectedResult,result);

        Mockito.verify(vehicleService).findVehicleByRegistrationPlate(vehicle.getRegistrationPlate());
        Mockito.verify(defectRepository).getDefectsByTypeAndVehicle(defectDto.getType(),vehicle);
        Mockito.verify(locationService).findLocationByXAndY(location.getX(), location.getY());
        Mockito.verify(imageService).saveImage(file,locations);
    }
    @DisplayName("should Throw EntityNotFoundException By Defect Null")
    @Test
    void shouldThrowEntityNotFoundExceptionByDefectNull() throws SQLException, IOException {
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

        defectDto.setLocations(locations);

        Defect defect=new Defect();
        defect.setLocations(locations);
        defect.setVehicle(vehicle);
        defect.setType("test-type");
        defect.setImage(new Image());


        Mockito.when(vehicleService.findVehicleByRegistrationPlate(vehicle.getRegistrationPlate())).thenReturn(vehicle);
        Mockito.when(defectRepository.getDefectsByTypeAndVehicle(defectDto.getType(), vehicle)).thenReturn(null);
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            defectManager.update(defectDto,file);
        });

        Assertions.assertNotNull(exception);
        Assertions.assertEquals(defectDto.getVehicle().getRegistrationPlate() +
                " plaka kodlu araç için böyle bir hata kayıtlı değildir.", exception.getMessage());
        Mockito.verify(vehicleService).findVehicleByRegistrationPlate(vehicle.getRegistrationPlate());
        Mockito.verify(defectRepository).getDefectsByTypeAndVehicle(defectDto.getType(),vehicle);
    }

    @AfterEach
    public void tearDown() {

    }


}