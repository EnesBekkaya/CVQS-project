package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.VehicleDto;
import com.cvqs.defectsaveservice.exception.EntityNotFoundException;
import com.cvqs.defectsaveservice.model.Vehicle;
import com.cvqs.defectsaveservice.repository.VehicleRepository;
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
 * VehicleManager sınıfı,VehicleService arayüzünden türetilmiştir ve
 *  araçlarla ilgili işlemleri yönetir. Bu sınıf, veritabanı işlemleri için VehicleRepository nesnelerini kullanmaktadır.
 *
 * @author Enes Bekkaya
 * @since  13.02.2023
 */
@Service
@RequiredArgsConstructor
public class VehicleManager implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper;
    private static final Logger LOGGER= LoggerFactory.getLogger(DefectManager.class);

    /**
     * Verilen VehicleDto nesnesini kullanarak bir Vehicle nesnesi oluşturur ve bu nesneyi veritabanına kaydeder.
     * @param vehicleDto kaydedilecek VehicleDto nesnesi
     * @throws ResponseStatusException kayıt plakasına sahip bir Vehicle nesnesi bulunduğunda fıraltılır
     * @return kaydedilen VehicleDto nesnesi
     *
     */
    @Override
    public VehicleDto save(VehicleDto vehicleDto) {
        if (vehicleRepository.findVehicleByRegistrationPlate(vehicleDto.getRegistrationPlate()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Bu plaka koduna sahip araç zaten kayıtlı.");
        }
        Vehicle vehicle =modelMapper.map(vehicleDto, Vehicle.class);
        return modelMapper.map(vehicleRepository.save(vehicle), VehicleDto.class);
    }

    /**
     *
     * Veritabanındaki tüm Vehicle nesnelerini bulur ve bunları VehicleDto nesnelerine dönüştürerek bir liste olarak döndürür.
     * @return Veritabanındaki tüm Vehicle nesnelerinin VehicleDto karşılıklarını içeren bir liste
     */
    @Override
    public List<VehicleDto> getAll() {
        List <Vehicle> vehicles = vehicleRepository.findAll();
        List<VehicleDto> vehicleDtos = vehicles.stream().map(defect1 -> modelMapper.map(defect1, VehicleDto.class)).collect(Collectors.toList());
        return vehicleDtos;
    }

    /**
     *
     * Verilen kayıt plakasına sahip bir Vehicle nesnesi bulur.
     * @param registrationPlate bulunacak Vehicle nesnesinin kayıt plakası
     * @return kayıt plakasına sahip Vehicle nesnesi
     * @throws EntityNotFoundException kayıt plakasına sahip bir Vehicle nesnesi bulunamadığında fırlatılır
     */
    @Override
    public Vehicle findVehicleByRegistrationPlate(String registrationPlate) {
        Vehicle vehicle = vehicleRepository.findVehicleByRegistrationPlate(registrationPlate);
        if(vehicle ==null) {
            LOGGER.error("Plakaya ait Araç bulunamadı");
            throw new EntityNotFoundException("Plakaya ait Araç bulunamadı");
        }
        return vehicle;
    }
}
