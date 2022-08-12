package br.com.rodrigo.cloudparking.service;

import br.com.rodrigo.cloudparking.dto.ParkingCreateDTO;
import br.com.rodrigo.cloudparking.dto.ParkingDTO;
import br.com.rodrigo.cloudparking.dto.ParkingUpdateDTO;
import br.com.rodrigo.cloudparking.entities.ParkingEntitie;
import br.com.rodrigo.cloudparking.exception.ParkingNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
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

    public ParkingDTO findById(String id) {
        ParkingDTO parkingDTO = objectMapper.convertValue(parkingMap.get(id), ParkingDTO.class);
        if(parkingDTO == null){
            throw new ParkingNotFoundException("O parking informado não existe");
        }
        return parkingDTO;

    }

    public ParkingDTO create(ParkingCreateDTO parkingDTO) {
        ParkingDTO parkingDTO1 = objectMapper.convertValue(parkingDTO, ParkingDTO.class);
        String uuid = getUUID();
        parkingDTO1.setEntryDate(LocalDateTime.now());
        parkingDTO1.setId(uuid);
        parkingMap.put(uuid, objectMapper.convertValue(parkingDTO1, ParkingEntitie.class));
        return parkingDTO1;
    }

    public ParkingUpdateDTO update (String id, ParkingUpdateDTO parkingDTO){
        ParkingDTO parkingRecovered = findById(id);
        parkingRecovered.setColor(parkingDTO.getColor());
        parkingRecovered.setBill(parkingDTO.getBill());
        parkingRecovered.setLicense(parkingDTO.getLicense());
        parkingRecovered.setState(parkingDTO.getState());
        return objectMapper.convertValue(parkingRecovered, ParkingUpdateDTO.class);
    }

    public void delete (String id){
        parkingMap.remove(id);
    }

    public ParkingDTO exit (String id){
        ParkingDTO parkingDTO = objectMapper.convertValue(parkingMap.get(id), ParkingDTO.class);
        parkingDTO.setExitDate(LocalDateTime.now());
        Duration duration = Duration.between(parkingDTO.getEntryDate(), parkingDTO.getExitDate());
        long seconds = Math.abs(duration.getSeconds());
        long hours = (seconds / 3600);
        seconds -= (hours * 3600);
        long minutes = seconds / 60;
        seconds -= (minutes * 60);
        if(hours < 1){
            parkingDTO.setBill(0.0);
        } else if (hours == 1) {
            parkingDTO.setBill(50.0);
        }else{
            parkingDTO.setBill(50.0 * hours);
        }
        return parkingDTO;
    }
}
