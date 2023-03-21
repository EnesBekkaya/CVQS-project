package com.cvqs.defectlistingservice.service.concretes;

import com.cvqs.defectlistingservice.client.DefectListingServiceClient;
import com.cvqs.defectlistingservice.dto.DefectDto;
import com.cvqs.defectlistingservice.dto.Location;
import com.cvqs.defectlistingservice.dto.Vehichle;
import junit.framework.TestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

 class DefectListingManagerTest extends TestCase {
    private DefectListingManager defectListingManager;
    private DefectListingServiceClient defectListingServiceClient;

    @BeforeEach
     public void setUp(){
        defectListingManager= Mockito.mock(DefectListingManager.class);
        defectListingServiceClient=Mockito.mock(DefectListingServiceClient.class);
        defectListingManager=new DefectListingManager(defectListingServiceClient);
    }

    @DisplayName("should return defect list")
    @Test
    void shouldReturnDefectList(){
        Location location1=new Location(100,200);
        Location location2=new Location(327,145);
        List< Location> locations=new ArrayList<>(Arrays.asList(location1,location2));

        Vehichle vehichle=new Vehichle("audi","34BA23");

        DefectDto defectDto1=new DefectDto("çizik",vehichle,locations);
        DefectDto defectDto2=new DefectDto("göçük",vehichle,locations);
        List<DefectDto> defectDtos=new ArrayList<>(Arrays.asList(defectDto1,defectDto2));

        List<DefectDto> expectedResult=new ArrayList<>(Arrays.asList(defectDto1,defectDto2));

        Mockito.when(defectListingServiceClient.getAllDefects()).thenReturn(ResponseEntity.ok(defectDtos));

        List<DefectDto> result=defectListingManager.getAllDefects();
        assertEquals(expectedResult,result);

        Mockito.verify(defectListingServiceClient).getAllDefects();

    }

     @DisplayName("should return defect by plate with registrationPlate")
     @Test
     void shouldReturnDefectByPlateWithregistrationPlate(){
         String registrationPlate="34BA23";

         Location location1=new Location(100,200);
         Location location2=new Location(327,145);
         List< Location> locations=new ArrayList<>(Arrays.asList(location1,location2));

         Vehichle vehichle=new Vehichle("audi","34BA23");

         DefectDto defectDto1=new DefectDto("çizik",vehichle,locations);
         DefectDto defectDto2=new DefectDto("göçük",vehichle,locations);
         List<DefectDto> defectDtos=new ArrayList<>(Arrays.asList(defectDto1,defectDto2));

         List<DefectDto> expectedResult=new ArrayList<>(Arrays.asList(defectDto1,defectDto2));

         Mockito.when(defectListingServiceClient.getDefectsByPlate(registrationPlate)).thenReturn(ResponseEntity.ok(defectDtos));

         List<DefectDto> result=defectListingManager.findDefectByPlate(registrationPlate);
         assertEquals(expectedResult,result);

         Mockito.verify(defectListingServiceClient).getDefectsByPlate(registrationPlate);

     }

     @AfterEach
    public void tearDown() {

     }

}