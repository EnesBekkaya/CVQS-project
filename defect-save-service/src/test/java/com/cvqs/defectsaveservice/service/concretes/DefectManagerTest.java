package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.DefectDto;
import com.cvqs.defectsaveservice.dto.LocationDto;
import com.cvqs.defectsaveservice.exception.EntityNotFoundException;
import com.cvqs.defectsaveservice.model.Defect;
import com.cvqs.defectsaveservice.model.Image;
import com.cvqs.defectsaveservice.model.Location;
import com.cvqs.defectsaveservice.model.Vehichle;
import com.cvqs.defectsaveservice.service.abstracts.ImageService;
import com.cvqs.defectsaveservice.service.abstracts.LocationService;
import com.cvqs.defectsaveservice.service.abstracts.VehichleService;
import junit.framework.TestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.modelmapper.ModelMapper;
import com.cvqs.defectsaveservice.repository.DefectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class DefectManagerTest extends TestCase {
    private DefectManager defectManager;
   private  DefectRepository defectRepository;
   private LocationService locationService;
   private VehichleService vehichleService;
   private  ModelMapper modelMapper;
    private ImageService imageService;
    @BeforeEach
    public void setUp() {
        defectRepository = Mockito.mock(DefectRepository.class);
        locationService = Mockito.mock(LocationService.class);
        vehichleService = Mockito.mock(VehichleService.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        imageService=Mockito.mock(ImageService.class);
        defectManager = new DefectManager(defectRepository,locationService,vehichleService, imageService,modelMapper);
    }


    @DisplayName("should save Defect By DefectDto when NewDefect and return DefectDto")
    @Test
    void shouldSaveDefectDtoByDefectDtoWhenNewDefect() throws SQLException, IOException {
        MultipartFile file = new MockMultipartFile("test.jpg", new byte[]{});

        Vehichle vehichle = new Vehichle();
        vehichle.setRegistrationPlate("ABC123");

        DefectDto defectDto = new DefectDto();
        defectDto.setType("test-type");
        defectDto.setVehichle(vehichle);

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
        defect.setVehichle(vehichle);
        defect.setType("test-type");
        defect.setImage(new Image());

        DefectDto expectedResult=defectDto;

        Mockito.when(vehichleService.findVehichleByRegistrationPlate(vehichle.getRegistrationPlate())).thenReturn(vehichle);
        Mockito.when(locationService.findLocationByXAndY(location.getX(), location.getY())).thenReturn(location);
        Mockito.when(imageService.saveImage(file, locations)).thenReturn( new Image());
        Mockito.when(defectRepository.getDefectsByTypeAndVehichle(defectDto.getType(), vehichle)).thenReturn(null);
        Mockito.when(modelMapper.map(defectRepository.save(defect),DefectDto.class)).thenReturn(defectDto);

        DefectDto result = defectManager.save(defectDto, file);

      assertEquals(expectedResult,result);
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


        Vehichle vehichle = new Vehichle();
        vehichle.setRegistrationPlate("ABC123");
        Defect defect1=new Defect();
        defect1.setLocations(locations);
        defect1.setVehichle(vehichle);
        defect1.setType("çizik");
        Defect defect2=new Defect();
        defect2.setLocations(locations);
        defect2.setVehichle(vehichle);
        defect2.setType("göçük");
        List<Defect> defects = Arrays.asList(defect1, defect2);

        Mockito.when(defectRepository.findAll()).thenReturn(defects);

        List<DefectDto> expectedDefectDtos = defects.stream()
                .map(defect -> modelMapper.map(defect, DefectDto.class))
                .collect(Collectors.toList());
        List<DefectDto> result = defectManager.getAll();

        assertEquals(expectedDefectDtos, result);
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
        Vehichle vehichle = new Vehichle();
        vehichle.setRegistrationPlate("ABC123");

        Defect defect1=new Defect();
        defect1.setLocations(locations);
        defect1.setVehichle(vehichle);
        defect1.setType("çizik");
        Defect defect2=new Defect();
        defect2.setLocations(locations);
        defect2.setVehichle(vehichle);
        defect2.setType("göçük");
        List<Defect> defects = Arrays.asList(defect1, defect2);
        List<DefectDto> expectedDefectDtos = defects.stream()
                .map(defect -> modelMapper.map(defect, DefectDto.class))
                .collect(Collectors.toList());

        Mockito.when(defectRepository.findByRegistrationPlate(vehichle.getRegistrationPlate())).thenReturn(defects);

        List<DefectDto> result = defectManager.findByRegistrationPlate(vehichle.getRegistrationPlate());


        // Assert
        assertEquals(expectedDefectDtos,result);
   }


    @DisplayName("should throw EntityNotFoundException when the parameter of the findVehichleByRegistrationPlate RegistrationPlate does not exist ")
    @Test
    void shouldThrowEntityNotFoundException_whenRegistrationPlateDoesNotExist() {
        Vehichle vehichle = new Vehichle();
        vehichle.setRegistrationPlate("ABC123");
        vehichle.setBrand("audi");
        List<Defect> defects = new ArrayList<>();

        Mockito.when(defectRepository.findByRegistrationPlate(vehichle.getRegistrationPlate())).thenReturn(defects);

        EntityNotFoundException exception = null;
        try {
            defectManager.findByRegistrationPlate(vehichle.getRegistrationPlate());
        } catch (EntityNotFoundException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals("Araca kayıtlı hata bulunamadı", exception.getMessage());
    }

    @AfterEach
    public void tearDown() {

    }


}