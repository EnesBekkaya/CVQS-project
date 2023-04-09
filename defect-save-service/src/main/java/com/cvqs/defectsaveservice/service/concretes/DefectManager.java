package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.DefectDto;
import com.cvqs.defectsaveservice.exception.EntityNotFoundException;
import com.cvqs.defectsaveservice.model.Defect;
import com.cvqs.defectsaveservice.model.Image;
import com.cvqs.defectsaveservice.model.Location;
import com.cvqs.defectsaveservice.model.Vehicle;
import com.cvqs.defectsaveservice.repository.DefectRepository;
import com.cvqs.defectsaveservice.service.abstracts.DefectService;
import com.cvqs.defectsaveservice.service.abstracts.ImageService;
import com.cvqs.defectsaveservice.service.abstracts.LocationService;
import com.cvqs.defectsaveservice.service.abstracts.VehicleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  DefectManager sınıfı, DefectService arayüzünden türetilmiştir ve
 *  hata yönetimi işlemlerini yönetir. Bu sınıf, veritabanı işlemleri için DefectRepository
 *  lokasyon işlemleri için LocationService, araç işlemleri için VehicleService
 *  resim işlemleri için ImageService} nesnelerini kullanmaktadır.
 *
 *  @author Enes Bekkaya
 *  @since  12.02.2023
 */
@Service
@RequiredArgsConstructor
public class    DefectManager implements DefectService {
    private final DefectRepository defectRepository;
    private final LocationService locationService;
    private final VehicleService vehicleService;
    private final ImageService imageService;
    private final ModelMapper modelMapper;
    private static final Logger LOGGER= LoggerFactory.getLogger(DefectManager.class);
    /**
     *
     *Verilen DefectDto nesnesine göre yeni bir Defect oluşturur veya mevcut bir Defect'in lokasyonlarını günceller ve geri döndürür.
     *
     * @param defectDto DefectDto türünde, kaydedilecek Defect nesnesinin bilgilerini içeren nesne
     * @param file MultipartFile türünde, kaydedilecek Defect nesnesine ait resim dosyası
     * @return Kaydedilen veya güncellenen DefectDto nesnesi
     * @throws IOException Eğer resim dosyası kaydedilemezse fırlatılır
     * @throws SQLException Eğer veritabanı işleminde bir hata olursa fırlatılır
     *
     */
    @Override
    public DefectDto save(DefectDto defectDto, MultipartFile file) throws IOException, SQLException {
        Vehicle vehicle = vehicleService.findVehicleByRegistrationPlate(defectDto.getVehicle().getRegistrationPlate());

        List<Location> locations=new ArrayList<>();
        Defect newDefect=new Defect();

        Defect defect=defectRepository.getDefectsByTypeAndVehicle(defectDto.getType(), vehicle);

        if(defect==null) {
            defectDto.getLocations().forEach(location -> {
                Location location1=locationService.findLocationByXAndY(location.getX(),location.getY());
                locations.add(location1);
            });
            newDefect.setType(defectDto.getType());
            newDefect.setVehicle(vehicle);
            newDefect.setLocations(locations);
            newDefect.setImage(imageService.saveImage(file,defectDto.getLocations()));
            return modelMapper.map(defectRepository.save(newDefect), DefectDto.class);
        }
        else {
            defectDto.getLocations().forEach(location -> {
                Location location1=locationService.findLocationByXAndY(location.getX(),location.getY());
                locations.add(location1);
            });
            defect.setLocations(locations);
            defect.setImage(imageService.saveImage(file,defectDto.getLocations()));
            return modelMapper.map(defectRepository.save(defect),DefectDto.class);
        }
    }
    /**
     * Tüm hasarları getiren metot.
     *
     * @return DefectDto tipindeki tüm hataları listesini döndürür.
     */
    @Override
    public List<DefectDto> getAll() {
        List <Defect> defects=defectRepository.findAll();
        return defects.stream().map(defect1 -> modelMapper.map(defect1,DefectDto.class)).collect(Collectors.toList());
    }
    /**
     * Verilen plakaya ait hataları getiren metot.
     *
     * @param registrationPlate hasarların arandığı aracın plakası
     * @return verilen plakaya ait hasarların listesini döndürür.
     * @throws EntityNotFoundException eğer verilen plakaya ait hasar bulunamazsa fırlatılır
     */
    @Override
    public List<DefectDto> findByRegistrationPlate(String registrationPlate) {
        List<Defect> defects=defectRepository.findByRegistrationPlate(registrationPlate);

        if(defects.isEmpty()) {
            LOGGER.error("Araca kayıtlı hata bulunamadı");
            throw new EntityNotFoundException("Araca kayıtlı hata bulunamadı");
        }
        return defects.stream().map(defect -> modelMapper.map(defect, DefectDto.class)).collect(Collectors.toList());
    }
    /**
     * Belirtilen plaka ve hata türüne ait hasar resmini getiren metot.
     *
     * @param registrationPlate hata görüntüsünün arandığı aracın plakası
     * @param defectType        hata görüntüsünün arandığı hasar türü
     * @return hasar görüntüsünün byte dizisi döndürür
     * @throws SQLException eğer veritabanı işlemleri sırasında bir hata oluşursa fırlatılır
     */
    @Override
    public byte[] getDefectImage(String registrationPlate, String defectType) throws SQLException {
        Defect defect=defectRepository.findDefectByregistrationPlateAndType(registrationPlate,defectType);
        Image image=imageService.getImage(defect.getImage());
        byte[] imageData = image.getData().getBytes(1, (int) image.getData().length());
        return imageData;
    }
    /**
     * Belirtilen sayfa numarası, sayfa boyutu ve sıralama ölçütüne göre kusurları sıralayarak, sayfalı bir şekilde döndüren metot.
     *
     * @param pageNo   döndürülecek sayfanın numarası
     * @param pageSize sayfa başına döndürülecek kusur sayısı
     * @param sortBy   kusurların nasıl sıralanacağına dair sıralama ölçütü
     * @return sayfalı şekilde sıralanmış kusur DTO'ları döndürür
     */
    public List<DefectDto> getDefectSorted(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Defect> defectDtos=defectRepository.findAll(paging);
        return  defectDtos.getContent().stream().map(defect -> modelMapper.map(defect, DefectDto.class)).collect(Collectors.toList());
    }


}
