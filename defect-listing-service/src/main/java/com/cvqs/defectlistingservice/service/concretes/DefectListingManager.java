package com.cvqs.defectlistingservice.service.concretes;


import com.cvqs.defectlistingservice.dto.DefectDto;
import com.cvqs.defectlistingservice.exception.EntityNotFoundException;
import com.cvqs.defectlistingservice.model.Defect;
import com.cvqs.defectlistingservice.model.Image;
import com.cvqs.defectlistingservice.repository.DefectListingRepository;
import com.cvqs.defectlistingservice.service.abstracts.DefectListingService;
import com.cvqs.defectlistingservice.service.abstracts.ImageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hata listeleme sınıfı
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
@Service
@RequiredArgsConstructor
public class DefectListingManager implements DefectListingService {
    private final DefectListingRepository defectListingRepository;
    private final ModelMapper modelMapper;
    private final ImageService imageService;
    private static final Logger LOGGER = LoggerFactory.getLogger(DefectListingManager.class);

    /**
     * Tüm hasarları getiren metot.
     *
     * @return DefectDto tipindeki tüm hataları listesini döndürür.
     */
    @Override
    public List<DefectDto> getAll() {
        List<Defect> defects = defectListingRepository.findAll();
        return defects.stream().map(defect1 -> modelMapper.map(defect1, DefectDto.class)).collect(Collectors.toList());
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
        List<Defect> defects = defectListingRepository.findByRegistrationPlate(registrationPlate);

        if (defects.isEmpty()) {
            LOGGER.warn("işlem başarısız!! {} plaka kodu için kayıtlı bir araç bulunamadı.",registrationPlate);
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
        Defect defect = defectListingRepository.findDefectByregistrationPlateAndType(registrationPlate, defectType);
        if(defect==null){
            LOGGER.warn("işlem başarısız!!{} plaka kodlu araç için{} böyle bir hata kayıtlı değil. ",registrationPlate,defectType);

            throw new EntityNotFoundException(registrationPlate +
                    " plaka kodlu araç için böyle bir hata kayıtlı değildir.");
        }
        Image image = imageService.getImage(defect.getImage());
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

        Page<Defect> defectDtos = defectListingRepository.findAll(paging);
        return defectDtos.getContent().stream().map(defect -> modelMapper.map(defect, DefectDto.class)).collect(Collectors.toList());
    }
}
