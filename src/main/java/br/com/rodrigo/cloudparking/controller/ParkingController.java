package br.com.rodrigo.cloudparking.controller;

import br.com.rodrigo.cloudparking.dto.ParkingDTO;
import br.com.rodrigo.cloudparking.service.ParkingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping(value = "/parking")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }


    @GetMapping
    public ResponseEntity<List<ParkingDTO>> findAll(){

        return ResponseEntity.ok(parkingService.findAll());
    }

}
