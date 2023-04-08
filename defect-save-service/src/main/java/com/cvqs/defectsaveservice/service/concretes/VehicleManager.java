package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.VehichleDto;
import com.cvqs.defectsaveservice.exception.EntityNotFoundException;
import com.cvqs.defectsaveservice.model.Vehicle;
import com.cvqs.defectsaveservice.repository.VehichleRepository;
import com.cvqs.defectsaveservice.service.abstracts.VehicleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
/**
 * VehichleManager sınıfı,VehichleService arayüzünden türetilmiştir ve
 *  araçlarla ilgili işlemleri yönetir. Bu sınıf, veritabanı işlemleri için VehichleRepository nesnelerini kullanmaktadır.
 *
 * @author Enes Bekkaya
 * @since  13.02.2023
 */
@Service
@RequiredArgsConstructor
public class VehicleManager implements VehicleService {
    private final VehichleRepository vehichleRepository;
    private final ModelMapper modelMapper;
    private static final Logger LOGGER= LoggerFactory.getLogger(DefectManager.class);

    /**
     * Verilen VehichleDto nesnesini kullanarak bir Vehichle nesnesi oluşturur ve bu nesneyi veritabanına kaydeder.
     * @param vehichleDto kaydedilecek VehichleDto nesnesi
     * @throws ResponseStatusException kayıt plakasına sahip bir Vehichle nesnesi bulunduğunda fıraltılır
     * @return kaydedilen VehichleDto nesnesi
     *
     */
    @Override
    public VehichleDto save(VehichleDto vehichleDto) {
        if (vehichleRepository.findVehichleByRegistrationPlate(vehichleDto.getRegistrationPlate()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Bu plaka koduna sahip araç zaten kayıtlı.");
        }
        Vehicle vehicle =modelMapper.map(vehichleDto, Vehicle.class);
        return modelMapper.map(vehichleRepository.save(vehicle), VehichleDto.class);
    }

    /**
     *
     * Veritabanındaki tüm Vehichle nesnelerini bulur ve bunları VehichleDto nesnelerine dönüştürerek bir liste olarak döndürür.
     * @return Veritabanındaki tüm Vehichle nesnelerinin VehichleDto karşılıklarını içeren bir liste
     */
    @Override
    public List<VehichleDto> getAll() {
        List <Vehicle> vehicles =vehichleRepository.findAll();
        List<VehichleDto> vehichleDtos= vehicles.stream().map(defect1 -> modelMapper.map(defect1,VehichleDto.class)).collect(Collectors.toList());
        return vehichleDtos;
    }

    /**
     *
     * Verilen kayıt plakasına sahip bir Vehichle nesnesi bulur.
     * @param registrationPlate bulunacak Vehichle nesnesinin kayıt plakası
     * @return kayıt plakasına sahip Vehichle nesnesi
     * @throws EntityNotFoundException kayıt plakasına sahip bir Vehichle nesnesi bulunamadığında fırlatılır
     */
    @Override
    public Vehicle findVehichleByRegistrationPlate(String registrationPlate) {
        Vehicle vehicle =vehichleRepository.findVehichleByRegistrationPlate(registrationPlate);
        if(vehicle ==null) {
            LOGGER.error("Plakaya ait Araç bulunamadı");
            throw new EntityNotFoundException("Plakaya ait Araç bulunamadı");
        }
        return vehicle;
    }
}
