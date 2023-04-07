package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.LocationDto;
import com.cvqs.defectsaveservice.model.Location;
import com.cvqs.defectsaveservice.repository.LocationRepository;
import com.cvqs.defectsaveservice.service.abstracts.LocationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
/**
 * LocationManager sınıfı, LocationService arayüzünden türetilmiştir ve
 *  konumlarla ilgili işlemleri yönetir. Bu sınıf, veritabanı işlemleri için LocationRepository nesnelerini kullanmaktadır.
 *
 * @author Enes Bekkaya
 * @since  13.02.2023
 */
@Service
@RequiredArgsConstructor
public class LocationManager implements LocationService {
    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;
    /**
     * Konumları depolamak için kullanılan konum deposuna verilen konumları kaydeder ve kaydedilen konumların DTO'larını döndürür.
     * @param locations kaydedilecek konumlar
     * @return kaydedilen konumların DTO'ları döndürür
     *
     */
    @Override
    public List<LocationDto> save(List<Location> locations) {
        List<Location> locations1 = locationRepository.saveAll(locations);
        return locations1.stream().map(location -> modelMapper.map(location, LocationDto.class)).collect(Collectors.toList());

    }
    /**
     *
     * Verilen X ve Y koordinatlarına sahip bir konumu arar. Eğer böyle bir konum yoksa, yeni bir konum oluşturur ve kaydeder.
     * @param x aranan konumun X koordinatı
     * @param y aranan konumun Y koordinatı
     * @return bulunan konum veya yeni oluşturulan konumu döndürür
    */
    @Override
    public Location findLocationByXAndY(int x, int y) {
        Location location = locationRepository.findLocationByXAndY(x, y);
        Location newLocation = new Location();

        if (location == null) {
            newLocation.setX(x);
            newLocation.setY(y);
            return locationRepository.save(newLocation);

        }
        else return location;

    }




}