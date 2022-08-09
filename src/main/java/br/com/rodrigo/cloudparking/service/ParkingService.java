package br.com.rodrigo.cloudparking.service;

import br.com.rodrigo.cloudparking.dto.ParkingDTO;
import br.com.rodrigo.cloudparking.entities.ParkingEntitie;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
public class ParkingService {

    private static Map<String , ParkingEntitie> parkingMap = new HashMap<>();

    private final ObjectMapper objectMapper;

    public ParkingService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    static {
        var id = getUUID();
       ParkingEntitie parking = new ParkingEntitie();
        parking.setColor("Black");
        parking.setLicense("MSS-1111");
        parking.setModel("VM GOL");
        parking.setState("PB");
        parkingMap.put(id, parking);
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-","");
    }

    public List<ParkingDTO> findAll(){
        return parkingMap
                .values()
                .stream()
                .map(convertParkingToDTO())
                .toList();
    }

    private Function<ParkingEntitie, ParkingDTO> convertParkingToDTO() {
        return parkingEntitie -> objectMapper.convertValue(parkingEntitie, ParkingDTO.class);
    }

}
