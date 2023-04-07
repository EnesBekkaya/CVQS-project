package com.cvqs.defectlistingservice.service.concretes;

import com.cvqs.defectlistingservice.client.DefectListingServiceClient;
import com.cvqs.defectlistingservice.dto.DefectDto;
import com.cvqs.defectlistingservice.exception.EntityNotFoundException;
import com.cvqs.defectlistingservice.service.abstracts.DefectListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
/**
 * Hata listeleme sınıfı
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
@Service
@RequiredArgsConstructor
public class DefectListingManager implements DefectListingService {

    private final DefectListingServiceClient defectSaveServiceClient;

    /**
     * getAllDefects metodu veritabanına kayıtlı olan bütün hataları döndürür.
     * @return sonuc DefectDto veri tipinde döndürür
     */
    @Override
    public List<DefectDto> getAllDefects() {
       List<DefectDto> defectDtos= defectSaveServiceClient.getAllDefects().getBody();
       return defectDtos;
    }
    /**
     * findDefectByPlate metodu plakaya göre veritabanına kayıtlı olan bütün hataları döndürür.
     * @param registrationPlate listenlenmek istenen hatanın ait olduğu aracın registrationPlate kodu.
     * @throws EntityNotFoundException verilen plaka koduna ait araç yoksa fırlatılır.
     * @return sonuc liste olarak DefectDto veri tipinde döndürür.
     */
    @Override
    public List<DefectDto>  findDefectByPlate(String registrationPlate) {
        try {
            List<DefectDto>  defectDto=defectSaveServiceClient.getDefectsByPlate(registrationPlate).getBody();
            return defectDto;
        }
        catch (Exception e){
            throw new EntityNotFoundException(" Araca kayıtlı hata bulunamadı");
        }
    }
    /**
     * getDefectImage metodu plakaya ve hata tipine göre veritabanına kayıtlı olan  hata resmini döndürür.
     * @param registrationPlate listenlenmek istenen hatanın ait olduğu aracın registrationPlate kodu.
     * @param defectType listenlenmek istenen hatanın tipi.
     * @return sonuc byte[] veri tipinde döndürür.
     */
    @Override
    public byte[] getDefectImage(String registrationPlate, String defectType) throws SQLException {
        return defectSaveServiceClient.getDefectImage(registrationPlate,defectType).getBody();
    }
    /**
     * getDefectSorted metodu sayfalama yapar..
     * @param pageNo sayfa sayısı.
     * @param pageSize sayfa büyüklüğü.
     * @param sortBy neye göre sıralancağı.
     * @return sonuc liste olarak DefectDto veri tipinde döndürür.
     */
    @Override
    public List<DefectDto> getDefectSorted(Integer pageNo, Integer pageSize, String sortBy) {
        return defectSaveServiceClient.getDefectSorted(pageNo,pageSize,sortBy).getBody();
    }


}
