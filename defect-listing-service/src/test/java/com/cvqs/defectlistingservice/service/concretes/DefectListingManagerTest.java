package com.cvqs.defectlistingservice.service.concretes;

import com.cvqs.defectlistingservice.client.DefectListingServiceClient;
import com.cvqs.defectlistingservice.dto.DefectDto;
import com.cvqs.defectlistingservice.dto.Location;
import com.cvqs.defectlistingservice.dto.Vehicle;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

 class DefectListingManagerTest  {
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

        Vehicle vehicle =new Vehicle("audi","34BA23");

        DefectDto defectDto1=new DefectDto("çizik", vehicle,locations);
        DefectDto defectDto2=new DefectDto("göçük", vehicle,locations);
        List<DefectDto> defectDtos=new ArrayList<>(Arrays.asList(defectDto1,defectDto2));

        List<DefectDto> expectedResult=new ArrayList<>(Arrays.asList(defectDto1,defectDto2));

        Mockito.when(defectListingServiceClient.getAllDefects()).thenReturn(ResponseEntity.ok(defectDtos));

        List<DefectDto> result=defectListingManager.getAllDefects();
        Assertions.assertEquals(expectedResult,result);

        Mockito.verify(defectListingServiceClient).getAllDefects();

    }

     @DisplayName("should return defect by plate with registrationPlate")
     @Test
     void shouldReturnDefectByPlateWithregistrationPlate(){
         String registrationPlate="34BA23";

         Location location1=new Location(100,200);
         Location location2=new Location(327,145);
         List< Location> locations=new ArrayList<>(Arrays.asList(location1,location2));

         Vehicle vehicle =new Vehicle("audi","34BA23");

         DefectDto defectDto1=new DefectDto("çizik", vehicle,locations);
         DefectDto defectDto2=new DefectDto("göçük", vehicle,locations);
         List<DefectDto> defectDtos=new ArrayList<>(Arrays.asList(defectDto1,defectDto2));

         List<DefectDto> expectedResult=new ArrayList<>(Arrays.asList(defectDto1,defectDto2));

         Mockito.when(defectListingServiceClient.getDefectsByPlate(registrationPlate)).thenReturn(ResponseEntity.ok(defectDtos));

         List<DefectDto> result=defectListingManager.findDefectByPlate(registrationPlate);
         Assertions.assertEquals(expectedResult,result);

         Mockito.verify(defectListingServiceClient).getDefectsByPlate(registrationPlate);

     }

     @DisplayName("should Return Byte By Plate With RegistrationPlate And DefectType")
     @Test
     void shouldReturnByteByPlateWithregistrationPlateAndDefectType() throws SQLException {
        String registrationPlate="34BA23";
        String defectType="çizik";

        byte[] expectedResult= "test-image".getBytes();
        Mockito.when(defectListingServiceClient.getDefectImage(registrationPlate,defectType)).thenReturn(ResponseEntity.ok(expectedResult));
         byte[] result = defectListingManager.getDefectImage(registrationPlate, defectType);
         Assertions.assertEquals(expectedResult,result);
         Mockito.verify(defectListingServiceClient).getDefectImage(registrationPlate,defectType);

     }
     @DisplayName("should Return List Of DefectDto By PageNo And PageSize And SortBy")
     @Test
     void shouldReturnListOfDefectDtoByPageNoAndPageSizeAndSortBy(){
        int pageNo=1;
        int pageSize=3;
        String sortBy="type";
         Location location1=new Location(100,200);
         Location location2=new Location(327,145);
         List< Location> locations=new ArrayList<>(Arrays.asList(location1,location2));

         Vehicle vehicle =new Vehicle("audi","34BA23");
         DefectDto defectDto1=new DefectDto("çizik", vehicle,locations);
         DefectDto defectDto2=new DefectDto("göçük", vehicle,locations);
         List<DefectDto> defectDtos=new ArrayList<>(Arrays.asList(defectDto1,defectDto2));
        List<DefectDto> expectedResult=defectDtos;
        Mockito.when(defectListingServiceClient.getDefectSorted(pageNo,pageSize,sortBy)).thenReturn(ResponseEntity.ok(defectDtos));
        List<DefectDto>result=defectListingManager.getDefectSorted(pageNo,pageSize,sortBy);
        Assertions.assertEquals(expectedResult,result);

        Mockito.verify(defectListingServiceClient).getDefectSorted(pageNo,pageSize,sortBy);
    }
     @AfterEach
    public void tearDown() {

     }

}