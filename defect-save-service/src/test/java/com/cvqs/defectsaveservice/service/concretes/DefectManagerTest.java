package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.DefectDto;
import com.cvqs.defectsaveservice.model.Defect;
import com.cvqs.defectsaveservice.model.Location;
import com.cvqs.defectsaveservice.model.Vehichle;
import com.cvqs.defectsaveservice.service.abstracts.LocationService;
import com.cvqs.defectsaveservice.service.abstracts.VehichleService;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.modelmapper.ModelMapper;
import com.cvqs.defectsaveservice.repository.DefectRepository;
import java.util.Arrays;
import java.util.List;


public class DefectManagerTest extends TestCase {
    private DefectManager defectManager;
   private  DefectRepository defectRepository;
   private LocationService locationService;
   private VehichleService vehichleService;
   private  ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        defectRepository = Mockito.mock(DefectRepository.class);
        locationService = Mockito.mock(LocationService.class);
        vehichleService = Mockito.mock(VehichleService.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        defectManager = new DefectManager(defectRepository,locationService,vehichleService, modelMapper);
    }


    @DisplayName("should save Defect By DefectDto when NewDefect and return DefectDto")
    @Test
    void shouldSaveDefectDtoByDefectDtoWhenNewDefect() {
        Vehichle vehichle=new Vehichle("vehichle1","BMW","34BA23",null);
        Location location1=new Location("location1",100,200,null);
        Location location2=new Location("location2",20,30,null);
        List<Location> locations=Arrays.asList(location1,location2);
        DefectDto defectDto=new DefectDto("cizik",vehichle,locations);

        Defect defect1=new Defect("defect1","cizik",vehichle,locations);

        DefectDto expectedResult=new DefectDto("cizik",vehichle,locations);
        Mockito.when(vehichleService.findVehichleByRegistrationPlate(defectDto.getVehichle().getRegistrationPlate())).thenReturn(vehichle);
        Mockito.when(defectRepository.getDefectsByTypeAndVehichle("test_type",vehichle)).thenReturn(null);
        Mockito.when(defectRepository.save(defect1)).thenReturn(defect1);
        DefectDto result = defectManager.save(defectDto);

        assertEquals(expectedResult,result);
        Mockito.verify(vehichleService).findVehichleByRegistrationPlate(defectDto.getVehichle().getRegistrationPlate());
        Mockito.verify(defectRepository).getDefectsByTypeAndVehichle("test_type",vehichle);
        Mockito.verify(defectRepository).save(defect1);
        Mockito.verify(defectRepository, new Times(1)).getDefectsByTypeAndVehichle(defectDto.getType(),vehichle);


    }

}